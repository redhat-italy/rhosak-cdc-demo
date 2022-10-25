mvn clean package -Dquarkus.package.type=uber-jar
mkdir target/ocp && cp -R target/*-runner.jar target/ocp

oc new-build --name=order-entry --binary=true -i=java:openjdk-11-ubi8
oc start-build order-entry --from-dir=./target/ocp --follow
oc new-app order-entry -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgresql:5432/cdc-order

