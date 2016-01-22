#ids Dockerfile
FROM base
MAINTAINER DevOps
RUN mkdir /home/devops/config/certificates
ADD supervisord.conf /usr/etc/supervisord.conf
CMD ["/usr/bin/supervisord"]
EXPOSE 10040
