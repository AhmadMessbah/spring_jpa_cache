FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/spring_jpa_cache-0.0.1-SNAPSHOT.jar spring_jpa_cache.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring_jpa_cache.jar"]