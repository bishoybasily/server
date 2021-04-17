FROM maven:3.8.1-jdk-11-slim as builder
WORKDIR /workspace
ADD src /workspace/src
ADD pom.xml /workspace/pom.xml
RUN mvn clean package --batch-mode --no-transfer-progress

FROM amazoncorretto:11-alpine
COPY --from=builder /workspace/target/server-final.jar app.jar
EXPOSE 80
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
