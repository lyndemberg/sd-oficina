FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY order2/target/order2-jar-with-dependencies.jar order2-jar-with-dependencies.jar
EXPOSE 4442
CMD ["java", "-jar", "order2-jar-with-dependencies.jar"]