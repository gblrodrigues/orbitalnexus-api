FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew \
    && ./gradlew --no-daemon clean test buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*-all.jar /app/app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]