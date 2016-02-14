#base Dockerfile
#FROM centos:6.6

RUN if [ -f "/vshare/base-images/docker/centos-6.6" ]; then
	docker load -i /vshare/base-images/docker/centos-6.6
else
	FROM centos:6.6
fi

MAINTAINER DevOps
#RUN yum install -y telnet
#RUN yum install -y wget
#RUN yum install -y tar

RUN if [ -f "/vshare/base-images/misc/telnet-0.17-59.el7.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/misc/telnet-0.17-59.el7.x86_64.rpm
else
	yum install -y telnet
fi

RUN if [ -f "/vshare/base-images/misc/wget-1.14-10.el7.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/misc/wget-1.14-10.el7.x86_64.rpm
else
	yum install -y wget
fi

RUN if [ -f "/vshare/base-images/misc/tar-1.26-29.el7.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/misc/tar-1.26-29.el7.x86_64.rpm
else
	yum install -y tar
fi

#RUN cd /opt;wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
"http://download.oracle.com/otn-pub/java/jdk/7u71-b14/jre-7u71-linux-x64.tar.gz";tar xvf jre-7u71-linux-x64.tar.gz;chown -R root: jre1.7.0_71;alternatives --install /usr/bin/java java /opt/jre1.7.0_71/bin/java 1;rm /opt/jre-7u71-linux-x64.tar.gz

RUN if [ -f "/vshare/base-images/jdk/jdk-8u45-linux-x64.rpm" ]; then
	sudo yum install -y /vshare/base-images/jdk/jdk-8u45-linux-x64.rpm
else
	sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.rpm";
	sudo rpm -Uvh jdk-8u45-linux-x64.rpm;
fi

#RUN yum install -y passwd
RUN if [ -f "/vshare/base-images/misc/passwd-0.79-4.el7.x86_64.rpm" ]; then
	sudo yum install -y /vshare/base-images/misc/passwd-0.79-4.el7.x86_64.rpm
else
	RUN yum install -y passwd
fi

#RUN yum install -y python-setuptools
RUN if [ -f "/vshare/base-images/misc/python-setuptools-0.9.8-4.el7.noarch.rpm" ]; then
	sudo yum install -y /vshare/base-images/misc/python-setuptools-0.9.8-4.el7.noarch.rpm
else
	RUN yum install -y python-setuptools
fi

ADD add-devops-user.sh /tmp/
RUN ./tmp/add-devops-user.sh

COPY elk-setup /tmp/elk-setup
#COPY logstash-forwarder.crt /tmp
RUN chmod -R 777 /tmp/*
RUN ./tmp/elk-setup/agent/setup-centos.sh /tmp/elk-setup/agent/config/logstash-forwarder.conf /tmp/elk-setup/agent/config/logstash-forwarder.crt /tmp/elk-setup/agent/config/logstash-forwarder.repo

#RUN rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
#COPY logstash-forwarder.repo  /etc/yum.repos.d/
#RUN yum -y install logstash-forwarder

#COPY logstash-forwarder.crt /etc/pki/tls/certs/
#COPY logstash-forwarder.conf  /etc/

#ADD omdagentsetuponcentos.sh /tmp/
#ADD check_mk_forclient /tmp/
#RUN ./tmp/omdagentsetuponcentos.sh
#RUN mkdir /tmp/omd-setup
#RUN mkdir /tmp/omd-setup/agent
#COPY omd-setup/agent/setup-centos.sh /tmp/
#COPY omd-setup/agent/config/check_mk /tmp/
#COPY omd-setup/agent/check_mk-agent-1.2.4p5-1.noarch.rpm /tmp/
#RUN ./tmp/setup-centos.sh /tmp/checkmk-agent-1.2.4p5-1.noarch.rpm /tmp/check_mk
COPY omd-setup /tmp/omd-setup
RUN chmod -R 777 /tmp/*
RUN ./tmp/omd-setup/agent/setup-centos.sh /tmp/omd-setup/agent/checkmk-agent-1.2.4p5-1.noarch.rpm /tmp/omd-setup/agent/config/check_mk

RUN mkdir /home/devops/config
RUN mkdir /home/devops/config/services
#COPY /mnt/git/DevOps_Platform/docker/config/services.properties /home/devops/config/
WORKDIR /usr/share/
RUN mkdir /var/log/organisation
RUN chown devops:devops /var/log/organisation
RUN easy_install supervisor
ENV JAVA_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:ParallelGCThreads=2 -verbose:gc -Xloggc:/var/log/organisation/Docker-01-tomcat-gc.log -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=2 -XX:GCLogFileSize=50M"
