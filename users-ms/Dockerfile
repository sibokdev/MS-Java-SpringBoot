FROM openjdk:11.0-jdk as builder

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

FROM openjdk:11.0-jdk

WORKDIR /app

COPY --from=builder /app/users-ms/target/users-ms-0.0.1-SNAPSHOT.jar .

ENV PORT 8000

EXPOSE $PORT

## entry point now is pointing to container folder with the compile
##ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]

## if entering in interagtive mode need to use cmd instead of entrypoint
CMD ["java", "-jar", "./users-ms-0.0.1-SNAPSHOT.jar"]