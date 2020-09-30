FROM java:8-jre
VOLUME /tmp
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/app.jar
ADD ./build/resources/main/application.properties /app/application.properties
ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/app/application.properties","-jar","/app/app.jar"]
EXPOSE 8083