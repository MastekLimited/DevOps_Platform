#base Dockerfile
FROM centos:6.6
MAINTAINER DevOps

RUN yum install -y telnet
RUN yum install -y wget
RUN yum install -y tar
RUN cd /opt;wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
"http://download.oracle.com/otn-pub/java/jdk/7u71-b14/jre-7u71-linux-x64.tar.gz";tar xvf jre-7u71-linux-x64.tar.gz;chown -R root: jre1.7.0_71;alternatives --install /usr/bin/java java /opt/jre1.7.0_71/bin/java 1;rm /opt/jre-7u71-linux-x64.tar.gz
RUN yum install -y passwd
RUN yum install -y python-setuptools
RUN easy_install supervisor

ADD add-devops-user.sh /tmp/
RUN ./tmp/add-devops-user.sh

COPY elk-setup /tmp/elk-setup
RUN chmod -R 777 /tmp/*
RUN ./tmp/elk-setup/agent/setup-centos.sh /tmp/elk-setup/agent/config/logstash-forwarder.conf /tmp/elk-setup/agent/config/logstash-forwarder.crt /tmp/elk-setup/agent/config/logstash-forwarder.repo

COPY omd-setup /tmp/omd-setup
RUN chmod -R 777 /tmp/*
RUN ./tmp/omd-setup/agent/setup-centos.sh /tmp/omd-setup/agent/checkmk-agent-1.2.4p5-1.noarch.rpm /tmp/omd-setup/agent/config/check_mk

RUN mkdir /home/devops/config
RUN mkdir /home/devops/config/services
WORKDIR /usr/share/
RUN mkdir /var/log/organisation
RUN chown devops:devops /var/log/organisation

ENV JAVA_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:ParallelGCThreads=2 -verbose:gc -Xloggc:/var/log/organisation/Docker-01-tomcat-gc.log -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=2 -XX:GCLogFileSize=50M"
