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
ADD setup.sh /tmp/
RUN ./tmp/setup.sh
RUN rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
COPY logstash-forwarder.repo  /etc/yum.repos.d/
RUN yum -y install logstash-forwarder

COPY logstash-forwarder.crt /etc/pki/tls/certs/
COPY logstash-forwarder.conf  /etc/

#ADD /mnt/gluster/repo/omd-setup /tmp/

#ADD omdagentsetuponcentos.sh /tmp/
#ADD check_mk_forclient /tmp/
#RUN ./tmp/omdagentsetuponcentos.sh
RUN mkdir /tmp/omd-setup
RUN mkdir /tmp/omd-setup/agent
ADD omd-setup/ /tmp/omd-setup/
RUN ./tmp/omd-setup/agent/setup-centos.sh /tmp/omd-setup/agent/check_mk-agent-1.2.4p5-1.noarch.rpm /tmp/omd-setup/agent/config/check_mk

RUN mkdir /home/devops/config
RUN mkdir /home/devops/config/services
#COPY /mnt/git/DevOps_Platform/docker/config/services.properties /home/devops/config/
WORKDIR /usr/share/
RUN mkdir /var/log/organisation
RUN chown devops:devops /var/log/organisation
RUN easy_install supervisor
ENV JAVA_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:ParallelGCThreads=2 -verbose:gc -Xloggc:/var/log/organisation/Docker-01-tomcat-gc.log -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=2 -XX:GCLogFileSize=50M"
