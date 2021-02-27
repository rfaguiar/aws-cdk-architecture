package com.rfaguiar;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Fn;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.CpuUtilizationScalingProps;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.ScalableTaskCount;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.events.targets.SnsTopic;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription;
import software.amazon.awscdk.services.sqs.DeadLetterQueue;
import software.amazon.awscdk.services.sqs.Queue;

import java.util.Map;

public class Service02Stack extends Stack {
    public Service02Stack(final Construct scope, final String id, Cluster cluster,
                          SnsTopic productEventsTopic, Table productEventsDynamoDb) {
        this(scope, id, null, cluster, productEventsTopic, productEventsDynamoDb);
    }

    public Service02Stack(final Construct scope, final String id, final StackProps props,
                          Cluster cluster, SnsTopic productEventsTopic, Table productEventsDynamoDb) {
        super(scope, id, props);

        Queue productEventsDlq = Queue.Builder.create(this, "ProductEventsDlq")
                .queueName("product-events-dlq")
                .build();

        DeadLetterQueue deadLetterQueue = DeadLetterQueue.builder()
                .queue(productEventsDlq)
                .maxReceiveCount(3)
                .build();

        Queue productEventsQueue = Queue.Builder.create(this, "ProductEvents")
                .queueName("product-events")
                .deadLetterQueue(deadLetterQueue)
                .build();

        SqsSubscription sqsSubscription = SqsSubscription.Builder
                .create(productEventsQueue)
                .build();
        productEventsTopic.getTopic().addSubscription(sqsSubscription);

        Map<String, String> envVariables = Map.of(
                "AWS_REGION","us-east-1",
                "AWS_SQS_QUEUE_PRODUCT_EVENTS_NAME", productEventsQueue.getQueueName()
                );

        ApplicationLoadBalancedFargateService service02 = ApplicationLoadBalancedFargateService.Builder
                .create(this, "ALB02")
                .serviceName("service-02")
                .cluster(cluster)
                .cpu(512)
                .memoryLimitMiB(1024)
                .listenerPort(80)
                .desiredCount(2)
                .healthCheckGracePeriod(Duration.seconds(5))
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("aws_project02")
                                .image(ContainerImage.fromRegistry("rfaguiar/aws-ecs-project02:1.5.0"))
                                .containerPort(9090)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder.create(this, "Service02LogGroup")
                                        .logGroupName("Service02")
                                        .removalPolicy(RemovalPolicy.DESTROY)
                                        .build())
                                    .streamPrefix("Service02")
                                    .build()))
                                .environment(envVariables)
                                .build())
                .publicLoadBalancer(true)
                .build();

        service02.getTargetGroup().configureHealthCheck(
                new HealthCheck.Builder()
                        .path("/actuator/health")
                        .port("9090")
                        .healthyHttpCodes("200")
                .build()
        );

        ScalableTaskCount scalableTaskCount = service02.getService().autoScaleTaskCount(
                EnableScalingProps.builder()
                        .minCapacity(2)
                        .maxCapacity(4)
                        .build()
        );

        scalableTaskCount.scaleOnCpuUtilization(
                "Service02AutoScaling",
                CpuUtilizationScalingProps.builder()
                            .targetUtilizationPercent(90)
                            .scaleInCooldown(Duration.seconds(60))
                            .scaleOutCooldown(Duration.seconds(60))
                        .build()
        );

        productEventsQueue.grantConsumeMessages(service02.getTaskDefinition().getTaskRole());
        productEventsDynamoDb.grantReadWriteData(service02.getTaskDefinition().getTaskRole());
    }
}
