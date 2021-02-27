FROM openjdk:15

# App
CMD mkdir /app
VOLUME /tmp
ARG DEPENDENCY=build
RUN echo ${DEPENDENCY}
COPY ${DEPENDENCY}/*.jar /app/app.jar

# Ports exposing
EXPOSE 9090

# Entry
ENTRYPOINT echo JAVA_OPTS=$JAVA_OPTS && java $JAVA_OPTS -jar /app/app.jar
