FROM openjdk:17

COPY target/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar
ENTRYPOINT ["java","-jar","customer-service.jar"]