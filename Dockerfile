FROM java:8
COPY build/libs/service-1.0.jar service.jar
EXPOSE 5000 
RUN bash -c 'touch /service.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/rca","-Djava.security.egd=file:/dev/./urandom","-jar","/service.jar"]