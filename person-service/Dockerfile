FROM openjdk:11
EXPOSE 8081
ADD target/person-service.jar person-service.jar
ENTRYPOINT ["java", "-jar", "/person-service.jar"]