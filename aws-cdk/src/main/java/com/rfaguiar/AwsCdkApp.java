package com.rfaguiar;

import software.amazon.awscdk.core.App;

public class AwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        VpcStack vpcStack = new VpcStack(app, "VPC");

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        RdsStack rdsStack = new RdsStack(app, "RDS", vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);

        SnsStack snsStack = new SnsStack(app, "SNS");

        InvoiceAppStack invoiceAppStack = new InvoiceAppStack(app, "InvoiceApp");

        Service01Stack service01Stack = new Service01Stack(
                app,
                "Service01",
                clusterStack.getCluster(),
                snsStack.getProductEventsTopic(),
                invoiceAppStack.getBucket(),
                invoiceAppStack.getS3InvoiceQueue());
        service01Stack.addDependency(clusterStack);
        service01Stack.addDependency(rdsStack);
        service01Stack.addDependency(snsStack);

        DynamoDBStack dynamoDB = new DynamoDBStack(app, "DynamoDB");

        Service02Stack service02Stack = new Service02Stack(
                app,
                "Service02",
                clusterStack.getCluster(),
                snsStack.getProductEventsTopic(),
                dynamoDB.getProductEventsDynamoDb());
        service02Stack.addDependency(clusterStack);
        service02Stack.addDependency(snsStack);
        service02Stack.addDependency(dynamoDB);

        app.synth();
    }
}
