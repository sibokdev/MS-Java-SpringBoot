FROM openjdk:11.0-jdk

## changing app folder on docker to compile
## we going to buil the project from the parent folder
WORKDIR /app/users-ms

COPY ./pom.xml /app
COPY ./users-ms .

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

## entry point now is pointing to container folder with the compile
ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]