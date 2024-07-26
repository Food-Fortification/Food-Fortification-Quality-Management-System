FROM eclipse-temurin:17-jre as builder
WORKDIR immudb
ARG JAR_FILE=target/immudb-exec.jar
COPY ${JAR_FILE} immudb.jar
RUN java -Djarmode=layertools -jar immudb.jar extract

FROM eclipse-temurin:17-jre
WORKDIR immudb
COPY --from=builder immudb/dependencies/ ./
COPY --from=builder immudb/spring-boot-loader/ ./
COPY --from=builder immudb/snapshot-dependencies/ ./
COPY --from=builder immudb/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]





