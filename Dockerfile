FROM eclipse-temurin:25.0.2_10-jdk

WORKDIR /app

COPY target/*.jar app.jar

COPY src/main/resources/users.json /data/users.json
COPY src/main/resources/notes.json /data/notes.json

ENV USER_NAME=Docker_Abdelrahman_Mohamed
ENV ID=Docker_55_22769
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
