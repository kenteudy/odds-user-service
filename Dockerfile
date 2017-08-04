FROM odds-base-container:latest
COPY  target/odds-user-service*.jar /home/jboss/odds-user-service.jar
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=""
ENV APP_JAR=odds-user-service.jar
