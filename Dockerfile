FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/ConnectionPoolJDBC-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-XX:StartFlightRecording=delay=2s,filename=/tmp/metric/myrecording.jfr,settings=profile", "-Ddb.pass=Dataparole", "-jar", "app.jar"]