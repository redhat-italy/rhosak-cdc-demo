# Shipment EndPoint
This application receives messages emitted by debezium on database changes made by Order-Entry API application and routed by Router application when delivery is equal to `shipment` 

## Pre Requisite
You must have configured a Red Hat Openshift Streams for Apache Kafka

### Service Account
Create service account
```
rhoas service-account create --short-description shipment-sa --file-format env --output-file /tmp/shipment-sa.env
cat /tmp/shipment-sa.env
```

Give the right acl
```shell
rhoas kafka acl grant-access --consumer --service-account <CLIENT-ID>  --topic shipment --group shipment
```
- Sample
```shell
rhoas kafka acl grant-access --consumer --service-account 1c0d7c5e-cf35-42d5-b56a-eed455e0df5e  --topic shipment --group shipment
```

Now you need to get the Bootstap server URL, Client ID and Client Secret then modify the file [application.properties](src/main/resources/application.properties)       
Client ID and Client Secret could be obtained from file shipment-sa.env created by command 'rhoas service-account create'
```
rhosak.bootstrap.servers=Bootstap server URL
rhosak.client.id=Client ID
rhosak.client.secret=Client Secret
```


## Packaging and deploy the application
### First Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
oc new-build --name=shipment --binary=true -i=java:openjdk-11-ubi8
oc start-build shipment --from-file=target/shipment-1.0.0-SNAPSHOT-runner.jar --follow
oc new-app shipment -e topic.sink.name=shipment
oc expose service/shipment
mvn clean
```
### Re-Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
oc start-build shipment --from-file=target/shipment-1.0.0-SNAPSHOT-runner.jar --follow
mvn clean
```