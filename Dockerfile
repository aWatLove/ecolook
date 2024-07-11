FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=build /app/target/ecolook-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
