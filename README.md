# Transaction REST API
REST API created to give support for transactions retrieving.

### Applications
There are three applications:
. A Service Register Server that will start in the following address: http://localhost:8082
. A Gateway application that will start in the following address: http://localhost:8083
. A Transaction REST API application that will start in the following address: http://localhost:8081

The API can be accessed by the Gateway application and directly by Transaction REST API application:
. Gateway application: http://localhost:8083/transactionapi/transactionmanagement/v1/<user Id>/transacoes/<year>/<month>
. Transaction REST API application: http://localhost:8081/v1/<user Id>/transacoes/<year>/<month>

### API available:
  - **/v1//<id>/transacoes/<ano>/<mes>** - Can be accessed via Http request method GET to retrieve a list of all transactions by user Id, year and month.

The contract format to return the transactons is as following:

```
[GET] /<id>/transacoes/<ano>/<mes>

Content-type: application/json

[
  {
     "description": "string(10, 120)"
     "date": "long(timestamp)"
     "value": "integer(-9.999.999, 9.999.999)"
     "duplicated": "boolean"
  }  
]
```

### Building
In root directory, br.challenge.transaction, build the applications executing the Gradle scripts as below:
```
gradle build
```

### Running
In root directory, br.challenge.transaction, start the applications executing the Gradle scripts as below:
```
gradle br.challenge.transaction.eureka:bootRun
gradle br.challenge.transaction.gateway:bootRun
gradle br.challenge.transaction.rest:bootRun
```

### Building Docker Images
In root directory, br.challenge.transaction, build the Docker images executing the scripts as below:
```
gradle br.challenge.transaction.rest:docker and also, in br.challenge.transaction.eureka directory, docker build --build-arg JAR_FILE=build/libs/br.challenge.transaction.eureka-1.0.0-SNAPSHOT-all.jar -t br.challenge.transaction.eureka .

gradle br.challenge.transaction.eureka:docker and also, in br.challenge.transaction.eureka directory, docker build --build-arg JAR_FILE=build/libs/br.challenge.transaction.eureka-1.0.0-SNAPSHOT-all.jar -t br.challenge.transaction.eureka .

gradle br.challenge.transaction.gateway:docker and also, in br.challenge.transaction.gateway directory, docker build --build-arg JAR_FILE=build/libs/br.challenge.transaction.gateway-1.0.0-SNAPSHOT-all.jar -t br.challenge.transaction.gateway .

gradle br.challenge.transaction.rest:docker and also, in br.challenge.transaction.rest directory, docker build --build-arg JAR_FILE=build/libs/br.challenge.transaction.rest-1.0.0-SNAPSHOT-all.jar -t br.challenge.transaction.rest .
```

### Running Docker Images
In root directory, br.challenge.transaction, build the Docker images executing the scripts as below:
```
docker run br.challenge.transaction.eureka

docker run br.challenge.transaction.gateway

docker run br.challenge.transaction.rest
```
