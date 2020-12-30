FROM maven:3.6.3-jdk-11-openj9

EXPOSE 8080
EXPOSE 8090


WORKDIR /project/service/

CMD mvn spring-boot:run  \
 -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8090 "
