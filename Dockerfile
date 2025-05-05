
FROM ubuntu

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk curl vim maven gnupg2 git && \
    apt-get clean

WORKDIR /opt

COPY target/checkcommonlib-*.jar checkcommonlib.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/checkcommonlib.jar"]
