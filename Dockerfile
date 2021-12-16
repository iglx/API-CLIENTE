FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPT=${ADDITIONAL_OPTS}

WORKDIR /opt/api_cliente

COPY /target/api_cliente*.jar api_cliente.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar api_cliente.jar --spring.profiles.active=${PROFILE}