#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER yoramnag@gmail.com

# Add the application's jar to the image
COPY target/transactions-SB3-0.0.1-SNAPSHOT.jar transactions-SB3-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "transactions-SB3-0.0.1-SNAPSHOT.jar"]