FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/shop-service-0.0.1-SNAPSHOT.jar shop-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shop-service.jar"]