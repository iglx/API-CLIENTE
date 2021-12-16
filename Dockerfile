FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPT=${ADDITIONAL_OPTS}

WORKDIR /opt/api-cliente

COPY /target/api-cliente*.jar api-cliente.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar api-cliente.jar --spring.profiles.active=${PROFILE}