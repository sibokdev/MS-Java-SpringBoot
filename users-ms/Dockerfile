FROM openjdk:11.0-jdk as builder
ARG MS_NAME=users-ms
## changing app folder on docker to compile
## we going to buil the project from the parent folder
WORKDIR /app/$MS_NAME

COPY ./pom.xml /app
## copiying maven content to save dependencies downloaded
COPY ./$MS_NAME/.mvn ./.mvn/
## copiying maven wrapper
COPY ./$MS_NAME/mvnw .
## copiying pom.xml
COPY ./$MS_NAME/pom.xml .

## maven command to skiptests and not compile tests, to not repackage, and delete target folder
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
##copiying code generated with t he flags
COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:11.0-jdk

WORKDIR /app
RUN mkdir ./logs
##redefine arg MS_NAME for the second build stage
ARG MS_NAME=users-ms
ARG TARGET_FOLDER=/app/$MS_NAME/target/

COPY --from=builder $TARGET_FOLDER/users-ms-0.0.1-SNAPSHOT.jar .

ENV PORT 8000

EXPOSE $PORT

## entry point now is pointing to container folder with the compile
##ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]

## if entering in interagtive mode need to use cmd instead of entrypoint
CMD ["java", "-jar", "./users-ms-0.0.1-SNAPSHOT.jar"]