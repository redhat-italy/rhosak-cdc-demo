# Order Entry API
Simply CRUD API application to populate DB

## Run Application Locally
```shell
podman run --name postgres -p 5432:5432 -e POSTGRES_DB=cdc-order -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpassword -d postgres
mvn compile quarkus:dev
```

## Packaging and deploy the application
### First Build
```shell
oc apply -f ocp_resources/postgresql.yaml
mvn clean package -Dquarkus.package.type=uber-jar
oc new-build --name=order-entry --binary=true -i=java:openjdk-11-ubi8
oc start-build order-entry --from-file=./target/orderEntry-1.0.0-SNAPSHOT-runner.jar --follow
oc new-app order-entry -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgresql:5432/cdc-order
oc expose svc/order-entry 
```
### Re-Build
```shell script
mvn clean package -Dquarkus.package.type=uber-jar
oc start-build shipment --from-file=target/orderEntry-1.0.0-SNAPSHOT-runner.jar --follow
mvn clean
```