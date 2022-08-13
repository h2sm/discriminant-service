FROM maven:3.8.6-jdk-11-slim AS build

COPY soapService/src soapService/src
COPY soapService/pom.xml soapService/

COPY webService/src webService/src
COPY webService/pom.xml webService/

RUN mvn -f /webService/pom.xml clean install
RUN mvn -f /soapService/pom.xml clean install


FROM openjdk:11-jre-slim
COPY --from=build webService/target/webService-1.0.jar /usr/local/lib/webService-1.0.jar
COPY --from=build soapService/target/soapService-1.0.jar /usr/local/lib/soapService-1.0.jar
EXPOSE 8080 8081

COPY runEverything.sh /runEverything.sh
RUN ["chmod", "+x", "/runEverything.sh"]

ENTRYPOINT ["./runEverything.sh"]