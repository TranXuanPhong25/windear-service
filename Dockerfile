FROM maven:3.8.5-openjdk-17 AS build
ENV DB_URL=${DB_URL}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_USERNAME=${DB_USERNAME}
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]