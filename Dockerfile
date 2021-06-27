FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/sd-report-1.0.jar ReportService.jar
ENTRYPOINT ["java", "-jar", "ReportService.jar"]
