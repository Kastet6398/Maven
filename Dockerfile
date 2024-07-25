FROM maven

COPY . .
RUN ["mvn", "clean", "package", "spring-boot:repackage"]
ENTRYPOINT ["java", "-jar", "target/hw.jar"]