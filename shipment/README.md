# Shipment EndPoint
This application receives messages emitted by debezium on database changes made by Order-Entry API application and routed by Router application when delivery is equal to `shipment` 

## Pre Requisite
You must have configured a Red Hat Openshift Streams for Apache Kafka

### Service Account
Create service account
```
rhoas service-account create --short-description shipment-sa --file-format env --output-file shipment-sa.env
```

Give the right acl
```
rhoas kafka acl grant-access --consumer --service-account <CLIENT-ID>  --topic shipment --group shipment
```
- Sample
```
rhoas kafka acl grant-access --consumer --service-account 445fb6cb-748e-4c16-865f-d895085a333c  --topic shipment --group shipment

```

Now you need to get the Bootstap server URL, Client ID and Client Secret then modify the file [application.properties](src/main/resources/application.properties)       
Client ID and Client Secret could be obtained from file shipment-sa.env created by command 'rhoas service-account create'

## Packaging and deploy the application
First Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp
oc new-build --name=shipment --binary=true -i=java:openjdk-11-ubi8
oc start-build shipment --from-dir=target/ocp --follow
oc new-app shipment -e topic.sink.name=shipment
mvn clean
```
Re-Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp
oc start-build shipment --from-dir=target/ocp --follow
mvn clean
```