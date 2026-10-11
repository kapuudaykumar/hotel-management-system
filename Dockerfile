FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar target/*.jar"]