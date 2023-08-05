FROM openjdk:11.0-jdk

## changing app folder on docker to compile
## we going to buil the project from the parent folder
WORKDIR /app/users-ms

COPY ./pom.xml /app
## copiying maven content to save dependencies downloaded
COPY ./users-ms/.mvn ./.mvn/
## copiying maven wrapper
COPY ./users-ms/mvnw .
## copiying pom.xml
COPY ./users-ms/pom.xml .

## maven command to skiptests and not compile tests, to not repackage, and delete target folder
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
##copiying code generated with t he flags
COPY ./users-ms/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

## entry point now is pointing to container folder with the compile
ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]