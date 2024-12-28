FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/kaiten-spring-app.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]