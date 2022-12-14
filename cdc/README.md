# Change Data Capture with Debezium

Helm chart to deploy a debezium KafkaConnect connected to postgresql to emitt database changes on Red Hat Openshift Stream for Apache Kafa (RHOSAK)

![Diagram](diagram.png)

## Pre Requisite
In ocp cluster install the operator `Red Hat Integration - AMQ Streams`

Create a service account and give the grant necessary to use the kafka instance
```shell script
rhoas service-account create --short-description rhosak-cdc-sa --file-format env --output-file /tmp/debezium-sa.env
cat /tmp/debezium-sa.env
rhoas kafka acl grant-access --consumer --producer --service-account <CLIENT-ID>  --topic all --group all 
(rhoas kafka acl grant-access --consumer --producer --service-account 00067379-19b3-4b86-a79e-a49a62fe871a --topic all --group all)
```

Now you need to get the Bootstap server URL, Client ID and Client Secret than modify the file [values.yaml](debezium-connect/values.yaml)          
Client ID could be obtained from file `debezium-sa.env` created by command `rhoas service-account create`   
Change the secret name in `debazium-sa.yaml` with value `debezium-account-credential`   
Bootstrap server could be obtained using below command   
```shell script
rhoas kafka describe |grep bootstrap
```

## Installation
Once you have copied the info into [value.yaml](debezium-connect/values.yaml) you can use `helm` to instantiate debezium objects
```shell script
helm install rhosak-cdc ./debezium-connect
```

Dry run, useful to verify the variable override
```shell script
helm template rhosak-cdc ./debezium-connect --values ./debezium-connect/values_rhosak.yaml --dry-run --debug
```

Upgrade
```shell script
helm upgrade rhosak-cdc ./debezium-connect
```



## Manage Connector

### Restart a Connector
```shell
oc annotate KafkaConnector cdc-connector-postgres strimzi.io/restart=true
```

### Restart a Connector Task
```shell
oc annotate KafkaConnector cdc-connector-postgres strimzi.io/restart-task=0
```


---
*Utility*
```shell
helm install rhosak-cdc ./debezium-connect --values ./debezium-connect/values_rhosak.yaml
helm upgrade rhosak-cdc ./debezium-connect --values ./debezium-connect/values_rhosak.yaml
 ```
