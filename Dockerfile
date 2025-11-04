# build stage
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests clean package

# runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/powertrack-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
