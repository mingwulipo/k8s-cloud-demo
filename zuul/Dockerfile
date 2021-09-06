FROM harbor.fastai.top/library/openjdk:8-jdk-alpine
COPY ./target/zuul.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=test","--spring.cloud.config.profile=test"]