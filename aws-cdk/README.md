# Welcome to your CDK Java project!

This is a blank project for Java development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!

### Install CDK  
```shell
npm install -g aws-cdk
```

### create stacks  
```shell
cdk deploy VPC Cluster Service01 RDS SNS Service02 InvoiceApp DynamoDB --parameters RDS:databasePassword=54213gvde12Et452
```

### destroy stacks
```shell
cdk destroy --all
```

Outputs Service01:
Service01.ALB01LoadBalancerDNS71443EB4 = Servi-ALB01-W531IOZ68DR6-2055662468.us-east-1.elb.amazonaws.com
Service01.ALB01ServiceURL8B1A7735 = http://Servi-ALB01-W531IOZ68DR6-2055662468.us-east-1.elb.amazonaws.com
