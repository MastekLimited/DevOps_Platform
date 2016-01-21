sudo java -jar employeeService.jar --spring.config.location=file:services.properties,classpath:/application.properties &
sudo java -jar projectService.jar --spring.config.location=file:services.properties,classpath:/application.properties &
sudo java -jar organisationWebApp.war --spring.config.location=file:services.properties,classpath:/application.properties &