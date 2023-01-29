FROM amazoncorretto:19
EXPOSE 8080
COPY src/main/resources/db/timetable.db src/main/resources/db/timetable.db
COPY target/timetable-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]