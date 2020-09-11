FROM java:8

VOLUME /tmp

ADD ./target/travel-automated-builder-0.0.1.jar ./travel-automated-builder-0.0.1.jar

RUN bash -c 'touch ./travel-automated-builder-0.0.1.jar'

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "./travel-automated-builder-0.0.1.jar"]