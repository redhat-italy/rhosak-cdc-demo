# UI
This project send a new order to [OrderEntry](../orderEntry/README.md) API project

# Local test
First of all you need to start OrderEntry application
```
npm install
export PORT=9090
export ORDERENTRYAPIURL=http://localhost:8080
node app.js
```

# OCP setup
To test this sub project on ocp:



```
oc new-build --image-stream=openshift/nodejs:latest --name=ui --binary=true
oc start-build ui --from-dir=ui 
oc new-app ui -e PORT="8080" -e ORDERENTRYAPIURL="orderEntry"
oc expose svc/ui
```