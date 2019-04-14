FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/person3-jar-with-dependencies.jar person3-jar-with-dependencies.jar
EXPOSE 2223
CMD ["java", "-jar", "person3-jar-with-dependencies.jar"]