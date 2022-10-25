# Router
This project uses Quarkus with camel extension in order to consume messages from Kafka Topic emitted by Debezium, implements content base routing to determinate the right topic to send the message

## Pre Requisite
You must have configured a Red Hat Stream for Apache Kafka 

Create service account and give the right acl

```
rhoas service-account create --short-description router-sa --file-format secret
rhoas kafka acl grant-access --producer --service-account <CLIENT-ID>  --topic shipment --group router
rhoas kafka acl grant-access --producer --service-account <CLIENT-ID>  --topic pickup --group router
rhoas kafka acl grant-access --consumer --service-account <CLIENT-ID>  --topic cdc.public.orders --group router
```

## Run Application
```
./mvnw compile quarkus:dev
```



## Packaging and deploy the application
### using maven
```shell script
./mvnw clean package
```

### using cli
```shell script
mvn package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp target/*-runner.jar target/ocp/
oc new-build --name=router --binary=true -i=java:openjdk-11-ubi8
oc start-build router --from-dir=./ocp --follow
oc new-app router
mvn clean
```