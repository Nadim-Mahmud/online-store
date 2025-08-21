FROM tomcat:9.0-jdk8

# Clean out default apps (ROOT, examples, etc.)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR into Tomcat's webapps as ROOT.war
COPY ./build/libs/OnlineStore-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose ports
EXPOSE 8080 5005

# Start Tomcat normally
CMD ["catalina.sh", "run"]