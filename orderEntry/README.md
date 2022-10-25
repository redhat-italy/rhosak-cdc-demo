# Order Entry API
Simply CRUD API application to populate DB

## Run Application Locally
```
podman run --name postgres -p 5432:5432 -e POSTGRES_DB=cdc-order -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpassword -d postgres
mvn compile quarkus:dev
```

## Deploy on OCP
### Deploy Manually
```
oc new-project order-entry
mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp
   

oc new-build --name=order-entry --binary=true -i=java:openjdk-11-ubi8
oc start-build order-entry --from-dir=./target/ocp --follow
oc new-app order-entry -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgresql:5432/cdc-order
oc expose svc/order-entry

export URL="http://$(oc get route order-entry -o jsonpath='{.spec.host}')"
echo "Application URL: $URL"

```
### Deploy With Quarkus Openshift Extension
Refer to [application.properties](src/main/resources/application.properties) for default configuration
https://quarkus.io/guides/all-config
```
mvn clean package -Dquarkus.profile=dev -DskipTests
```