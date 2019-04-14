FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/person1-jar-with-dependencies.jar person1-jar-with-dependencies.jar
EXPOSE 2221
CMD ["java", "-jar", "person1-jar-with-dependencies.jar"]