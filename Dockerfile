FROM openjdk:17-jdk-slim
WORKDIR /app
COPY pom.xml ./
COPY src ./src
COPY /target/*.jar /demo.jar
CMD ["java", "-jar", "/demo.jar"]
