FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/store1-jar-with-dependencies.jar store1-jar-with-dependencies.jar
EXPOSE 1111
CMD ["java", "-jar", "store1-jar-with-dependencies.jar"]