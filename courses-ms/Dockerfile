FROM openjdk:11.0-jdk as builder

## changing app folder on docker to compile
## we going to buil the project from the parent folder
WORKDIR /app/courses-ms

COPY ./pom.xml /app
## copiying maven content to save dependencies downloaded
COPY ./courses-ms/.mvn ./.mvn/
## copiying maven wrapper
COPY ./courses-ms/mvnw .
## copiying pom.xml
COPY ./courses-ms/pom.xml .

## maven command to skiptests and not compile tests, to not repackage, and delete target folder
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
##copiying code generated with t he flags
COPY ./courses-ms/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:11.0-jdk

WORKDIR /app

COPY --from=builder /app/courses-ms/target/courses-ms-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

## entry point now is pointing to container folder with the compile
##ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]

## if entering in interagtive mode need to use cmd instead of entrypoint
CMD ["java", "-jar", "./courses-ms-0.0.1-SNAPSHOT.jar"]