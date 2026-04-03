# syntax=docker/dockerfile:1

# Stage 1: unpack Spring Boot fat jar into layers
FROM eclipse-temurin:23-jdk AS build

LABEL org.opencontainers.image.title="config-server"

ARG JAR_FILE
WORKDIR /workspace
COPY ${JAR_FILE} app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /workspace/app.jar)

# Stage 2: run app on JDK 23
FROM eclipse-temurin:23-jdk

RUN apt-get update \
	&& apt-get install -y --no-install-recommends curl \
	&& rm -rf /var/lib/apt/lists/*

VOLUME /tmp
WORKDIR /app
EXPOSE 8071

ARG DEPENDENCY=/workspace/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes ./

ENTRYPOINT ["java","-cp",".:./lib/*","com.okhanzhin.config.server.ConfigurationServerApplication"]

