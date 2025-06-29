FROM openjdk:17.0-jdk-slim
WORKDIR /app
COPY UserSpringSecurity-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "/app/UserSpringSecurity-0.0.1-SNAPSHOT.jar" ]
