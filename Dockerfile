FROM maven AS maven
COPY . .
RUN mvn clean package -Denable-preview

FROM openjdk:21
COPY --from=maven /target/hw.jar hw.jar
ENTRYPOINT ["java","--enable-preview","-jar","hw.jar"]
