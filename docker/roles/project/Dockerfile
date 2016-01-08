#ids Dockerfile
FROM base
MAINTAINER DevOps
ADD supervisord.conf /usr/etc/supervisord.conf
CMD ["/usr/bin/supervisord"]
#CMD ["java", "-jar", "projectService.jar", "--spring.config.location=file:/home/devops/config/services.properties,classpath:/application.properties &"]
EXPOSE 8088