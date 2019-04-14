FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/customer3-1.0-SNAPSHOT-jar-with-dependencies.jar customer3-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 3333
CMD ["java", "-jar", "customer3-1.0-SNAPSHOT-jar-with-dependencies.jar"]