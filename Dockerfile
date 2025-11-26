# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Tomcat
FROM tomcat:10.1-jdk17

# 1. Clean default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# 2. CHANGE TOMCAT PORT FROM 8080 TO 9090
# We use 'sed' to edit the server.xml file inside the image
RUN sed -i 's/port="8080"/port="9090"/' /usr/local/tomcat/conf/server.xml

# 3. Copy WAR file
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose 9090 (Internal Container Port)
EXPOSE 9090
CMD ["catalina.sh", "run"]