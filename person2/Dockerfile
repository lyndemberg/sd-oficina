FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/person2-1.0-SNAPSHOT-jar-with-dependencies.jar person2-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 2222
CMD ["java", "-jar", "person2-1.0-SNAPSHOT-jar-with-dependencies.jar"]