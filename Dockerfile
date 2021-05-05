FROM mariadb:latest

ARG RUN_JAVA_VERSION=1.3.8
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

RUN mkdir -p /usr/share/man/man1
RUN apt update
RUN apt install -y gnupg
RUN apt install -y apt-transport-https
RUN apt install -y software-properties-common wget
RUN apt install -y curl
RUN apt install -y ca-certificates
RUN apt update
RUN echo "deb http://ftp.de.debian.org/debian stretch main" | tee -a /etc/apt/sources.list
RUN apt-get install -y openjdk-11-jdk
RUN mkdir /deployments \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

EXPOSE 8081

ADD build/lib/* /deployments/lib/
ADD build/*-runner.jar /deployments/app.jar

RUN chown 1001 /deployments/setup.sh \
    && chmod 540 /deployments/setup.sh

ENTRYPOINT ["/deployments/run-java.sh"]