FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY order1/target/order1-jar-with-dependencies.jar order1-jar-with-dependencies.jar
EXPOSE 4441
CMD ["java", "-jar", "order1-jar-with-dependencies.jar"]