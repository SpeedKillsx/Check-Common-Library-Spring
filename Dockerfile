FROM ubuntu
RUN apt-get update && apt-get install openjdk-17-jdk curl vim -y
WORKDIR opt
ADD target/checkcommonlib-*.jar checkcommonlib
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/checkcommonlib.jar"]