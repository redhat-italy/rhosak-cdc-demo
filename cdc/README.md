# Change Data Capture with Debezium

Helm chart to deploy a debezium KafkaConnect connected to postgresql to emitt database changes on Red Hat Openshift Stream for Apache Kafa (RHOSAK)

## Prerequisite
In ocp cluster install the operator `Red Hat Integration - AMQ Streams`

Create a service account and give the grant necessary to use the kafka instance
```shell script
rhoas service-account create --short-description cdc-demo-sa --file-format secret --output-file debezium-sa.yaml
rhoas kafka acl grant-access --consumer --producer --service-account <CLIENT-ID>  --topic all --group all 
(rhoas kafka acl grant-access --consumer --producer --service-account cec9e5f3-fbc3-4e21-a9d2-5840ab3605a9 --topic all --group all)
```

Now you need to get the Bootstap server URL, Client ID and modify the file [values.yaml](debezium-connect/values.yaml)       
Client ID could be obtained from file debezium-sa.yaml created by command 'rhoas service-account create'   
Bootstrap server could be obtained using below command
```shell script
rhoas kafka describe --name cdc |grep bootstrap
```

## Installation

Once you have copied the info into [value.yaml](debezium-connect/values.yaml) you can use `helm` to instantiate debezium objects
```shell script
oc apply -f debezium-sa.yaml
helm install cdc ./debezium-connect
```

