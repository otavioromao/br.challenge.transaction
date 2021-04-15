FROM openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /app
ARG JAR_FILE=build/lib/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 8081