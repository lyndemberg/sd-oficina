FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/store2-jar-with-dependencies.jar store2-jar-with-dependencies.jar
EXPOSE 1112
CMD ["java", "-jar", "store2-jar-with-dependencies.jar"]