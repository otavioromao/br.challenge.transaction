# Transaction REST API
REST API created to give support for transactions retrieving.

### Application
A Transaction REST API application that will start in the following address: http://localhost:8081/v1/userId/transacoes/year/month

### API available
  - **/\<user Id>/transacoes/\<year>/\<month>** - Can be accessed via Http request method GET to retrieve a list of all transactions by user Id, year and month.

The contract format to return the transactons is as following:

```
[GET] /<id>/transacoes/<ano>/<mes>

Content-type: application/json

[
  {
     "description": "string(10, 120)"
     "date": long(timestamp)
     "value": integer(-9.999.999, 9.999.999)
     "duplicated": boolean
  }  
]
```

### Building
In root directory, br.challenge.transaction, build the application executing the Gradle scripts as below:
```
gradlew build
```

### Running
In root directory, br.challenge.transaction, start the application executing the Gradle scripts as below:
```
gradlew bootRun
```

### Building Docker Images
In root directory, br.challenge.transaction, build the Docker images executing the scripts as below, where X.X.X-SNAPSHOT is the version of the application:
```
gradlew docker

and also

docker build --build-arg JAR_FILE=build/lib/br.challenge.transaction-X.X.X-SNAPSHOT-all.jar -t br.challenge.transaction .
```

### Running Docker Images
In root directory, br.challenge.transaction, start the Docker image executing the script as below:
```
gradlew dockerRun

and also

docker run -p 8081:8081 br.challenge.transaction
```
