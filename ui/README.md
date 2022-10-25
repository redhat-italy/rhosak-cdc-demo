# UI
This project send a new order to [OrderEntry](../orderEntry/README.md) API project

## Local test
First of all you need to start OrderEntry application
```
npm install
export PORT=9090
export ORDERENTRYAPIURL=http://localhost:8080
node app.js
```

## OCP setup
### Prerequisite
Get the route of OrderEntry Project
```
export ORDER_ENTRY_URL="$(oc get route order-entry -o jsonpath='{.spec.host}')"
```


To deploy this sub project on ocp:
```
oc new-build --image-stream=openshift/nodejs:latest --name=ui --binary=true
oc start-build ui --from-dir=. --follow
oc new-app ui -e PORT="8080" -e ORDERENTRYAPIURL=http://$ORDER_ENTRY_URL
oc expose svc/ui
```