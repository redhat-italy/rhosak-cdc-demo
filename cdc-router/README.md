# Router
This project uses Quarkus with camel extension in order to consume messages from Kafka Topic emitted by Debezium, implements content base routing to determinate the right topic to send the message

## Pre Requisite
You must have configured a Red Hat Openshift Streams for Apache Kafka

### Create Topic
You can create topics via web ui or alternatively you can use the rhoas cli
```
rhoas kafka topic create --name shipment
rhoas kafka topic create --name pickup
```


## Service Account
Create service account
```
rhoas service-account create --short-description router-sa --file-format env --output-file router-sa.env
```

Give the right acl
```
rhoas kafka acl grant-access --producer --service-account <CLIENT-ID>  --topic shipment --group router
rhoas kafka acl grant-access --producer --service-account <CLIENT-ID>  --topic pickup --group router
rhoas kafka acl grant-access --consumer --service-account <CLIENT-ID>  --topic cdc.public.orders --group router
```
- Sample
```
rhoas kafka acl grant-access --producer --service-account 95de081c-a007-407c-bb0d-d12d1edd7f04  --topic shipment
rhoas kafka acl grant-access --producer --service-account 95de081c-a007-407c-bb0d-d12d1edd7f04  --topic pickup
rhoas kafka acl grant-access --consumer --service-account 95de081c-a007-407c-bb0d-d12d1edd7f04  --topic cdc.public.orders --group router
```

Now you need to get the Bootstap server URL, Client ID and Client Secret then modify the file [application.properties](src/main/resources/application.properties)       
Client ID and Client Secret could be obtained from file router-sa.env created by command 'rhoas service-account create'


## Packaging and deploy the application
First Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp
oc new-build --name=cdc-router --binary=true -i=java:openjdk-11-ubi8
oc start-build cdc-router --from-dir=target/ocp --follow
oc new-app cdc-router
mvn clean
```
Re-Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp
oc start-build cdc-router --from-dir=target/ocp --follow
mvn clean
```