# syntax=docker/dockerfile:1

# Stage 1: unpack Spring Boot fat jar into layers
FROM eclipse-temurin:23-jdk AS build

LABEL org.opencontainers.image.title="workstation-service"

ARG JAR_FILE
WORKDIR /workspace
COPY ${JAR_FILE} app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /workspace/app.jar)

# Stage 2: run app on JDK 23
FROM eclipse-temurin:23-jdk

VOLUME /tmp
WORKDIR /app
EXPOSE 8082

ARG DEPENDENCY=/workspace/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes ./

ENTRYPOINT ["java","-cp",".:./lib/*","com.okhanzhin.workstation.service.WorkstationServiceApplicationKt"]

