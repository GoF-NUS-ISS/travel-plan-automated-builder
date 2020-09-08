FROM java:8

VOLUME /tmp

ADD ./target/microservicecloud.jar ./travel_builder.jar

RUN bash -c 'touch ./travel_builder.jar'

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "./travel_builder.jar"]