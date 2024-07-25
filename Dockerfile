FROM maven

COPY . .
ENTRYPOINT ["mvn", "spring-boot:run", "-X"]