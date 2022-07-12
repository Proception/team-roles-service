FROM maven:3.8.2-jdk-11
WORKDIR /team-roles-service
COPY . .
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run