FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY target/oficina-webapp.jar oficina-webapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","oficina-webapp.jar"]