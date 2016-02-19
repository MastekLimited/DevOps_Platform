#base Dockerfile
FROM centos:7
MAINTAINER DevOps
CMD ["/usr/sbin/init"]

COPY installables /tmp/installables
ADD setup-centos.sh /tmp/
RUN ./tmp/setup-centos.sh /tmp/installables

ADD install-supervisor.sh /tmp/
RUN ./tmp/install-supervisor.sh /tmp/installables

ADD add-devops-user.sh /tmp/
RUN ./tmp/add-devops-user.sh

COPY elk-setup /tmp/elk-setup
RUN chmod -R 777 /tmp/*
RUN ./tmp/elk-setup/agent/setup-centos.sh /tmp/elk-setup/agent/config /tmp/elk-setup/agent/installables

COPY omd-setup /tmp/omd-setup
RUN chmod -R 777 /tmp/*
RUN ./tmp/omd-setup/agent/setup-centos.sh /tmp/omd-setup/agent/config/check_mk /tmp/omd-setup/agent/installables

RUN mkdir /home/devops/config
RUN mkdir /home/devops/config/services
WORKDIR /usr/share/
RUN mkdir /var/log/organisation
RUN chown devops:devops /var/log/organisation

ENV JAVA_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:ParallelGCThreads=2 -verbose:gc -Xloggc:/var/log/organisation/Docker-01-tomcat-gc.log -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=2 -XX:GCLogFileSize=50M"
