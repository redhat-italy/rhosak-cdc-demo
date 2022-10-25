# Quarkus play

# Run Application
```
./mvnw compile quarkus:dev
```

# Deploy on OCP
## Deploy Manually
```
oc new-project quarkus-play
mvn clean package
mkdir target/ocp   
cp -R target/lib target/ocp
cp -R target/*-runner.jar target/ocp

oc policy add-role-to-user view system:serviceaccount:anas:default
oc new-build --name=quarkus-play --binary=true -i=java:openjdk-11-ubi8
oc start-build quarkus-play --from-dir=./target/ocp --follow
oc new-app quarkus-play
oc expose svc/quarkus-play

export URL="http://$(oc get route quarkus-play -o jsonpath='{.spec.host}')"
echo "Application URL: $URL"

curl $URL/hello
curl $URL/hello/greeting/mauiroma
```
## Deploy With Quarkus Openshift Extension
Refer to [application.properties](src/main/resources/application.properties) for default configuration
https://quarkus.io/guides/all-config
```
mvn clean package -Dquarkus.profile=dev -DskipTests
```
## Deploy as a Serverless
```
oc appy -f knative/service.yaml
```
## Deploy with Helm
```
cd helm
helm create quarkus-play

```



oc create configmap app-variable --from-file=configmap/app-variable.properties
