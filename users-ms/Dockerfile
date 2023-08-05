FROM openjdk:11.0-jdk

WORKDIR /app

COPY ./target/users-ms-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "users-ms-0.0.1-SNAPSHOT.jar"]