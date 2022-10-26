# Order Entry API
Simply CRUD API application to populate DB

## Run Application Locally
```shell
podman run --name postgres -p 5432:5432 -e POSTGRES_DB=cdc-order -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpassword -d postgres
mvn compile quarkus:dev
cd ui
npm install
export PORT=9090
export ORDERENTRYAPIURL=http://localhost:8080
node app.js
```

## Deploy on OCP
### Deploy Oder Entry Quarkus Application
```shell
oc apply -f ocp_resources/postgresql.yaml
mvn clean package -Dquarkus.package.type=uber-jar
oc new-build --name=order-entry --binary=true -i=java:openjdk-11-ubi8
oc start-build order-entry --from-file=./target/orderEntry-1.0.0-SNAPSHOT-runner.jar --follow
oc new-app order-entry -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgresql:5432/cdc-order
oc expose svc/order-entry 
export ORDER_ENTRY_URL="$(oc get route order-entry -o jsonpath='{.spec.host}')"
```


### Deploy Oder Entry Node.js Web Interface
```shell
cd ui
oc new-build --image-stream=openshift/nodejs:latest --name=ui --binary=true
oc start-build ui --from-dir=./ui --follow
oc new-app ui -e PORT="8080" -e ORDERENTRYAPIURL=http://$ORDER_ENTRY_URL
oc expose svc/ui
```