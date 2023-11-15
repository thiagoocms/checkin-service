FROM amazoncorretto:11
EXPOSE 5000
ARG JAR_FILE=target/*.jar
ENTRYPOINT ["java","-jar","/app.jar","com.nassau.checkinservice.CheckinServiceApplication"]
CMD java -XX:MaxRAMPercentage=80.0 -XX:MinRAMPercentage=50.0 -XX:MaxMetaspaceSize=256M -XX:MaxDirectMemorySize=50M -XX:ReservedCodeCacheSize=250M -jar /checkin-service.jar
