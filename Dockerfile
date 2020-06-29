FROM maven:3.5-jdk-8-alpine as builder

WORKDIR /Users/pouch
COPY pom.xml .
COPY src ./src

RUN mvn clean install spring-boot:repackage -DskipTests

FROM openjdk:8-jre-alpine

COPY --from=builder /Users/pouch/target/pouch-0.0.1-SNAPSHOT.jar /pouch.jar

CMD ["java","-Dserver.port=8080", "-jar","/pouch.jar"]
EXPOSE 8080