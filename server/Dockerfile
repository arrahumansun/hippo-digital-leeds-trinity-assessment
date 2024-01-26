FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/leeds-trinity-assessment-task-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} assessment-task-app.jar
ENTRYPOINT ["java","-jar","/assessment-task-app.jar"]