FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8888
ADD /target/diploma-cloudstorage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]