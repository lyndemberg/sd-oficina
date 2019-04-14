FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/customer2-jar-with-dependencies.jar customer2-jar-with-dependencies.jar
EXPOSE 3332
CMD ["java", "-jar", "customer2-jar-with-dependencies.jar"]