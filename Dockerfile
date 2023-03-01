FROM openjdk:17
EXPOSE 9000
ADD target/Uzum-0.0.1-SNAPSHOT.jar Uzum.jar
ENTRYPOINT ["java", "-jar", "/DockerSpringdemo.jar"]