FROM openjdk:11.0-jdk as builder
ARG MS_NAME=courses-ms
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
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
##copiying code generated with t he flags
COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:11.0-jdk

WORKDIR /app
RUN mkdir ./logs
ARG MS_NAME=courses-ms
ARG TARGET_FOLDER=/app/$MS_NAME/target
COPY --from=builder $TARGET_FOLDER/courses-ms-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

## entry point now is pointing to container folder with the compile
##ENTRYPOINT ["java", "-jar", "./target/users-ms-0.0.1-SNAPSHOT.jar"]

## if entering in interagtive mode need to use cmd instead of entrypoint
CMD ["java", "-jar", "./courses-ms-0.0.1-SNAPSHOT.jar"]