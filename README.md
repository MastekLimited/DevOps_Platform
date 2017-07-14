# Introduction
DevOps suite aims at providing end to end production like environment from the day one of the project. We have integrated Jenkins, Sonarqube, OMD/Nagios, ELK & Docker to provide a full stack production environment as part of this initiative. Environments can be replicated easily for dev/test/UAT/Path to live/Production using the integrated scripts.

##### Traditional Approach for Application Deployment and Maintenance

In the traditional model of deploying and maintaining an application we do the things such as:
1.	Create VMs for deployment of different services
2.	Install OS on these VMs
3.	Install application servers on this OS (tomcat, jboss, etc)
4.	Deploy the archive file on the application servers (war, jar, ear)
5.	Provide access permissions to these VMs for the developers or testers to access log files if anything goes wrong in the application.
6.	Create persistence store for holding the application data i.e. Database, Directory installation, etc.
7.	Execute the database scripts on the installed database
8.	Setup the service monitoring tools
9.	Start the application servers
10.	Repeat all the above steps for each test and production environment
11.	Manually setup other servers such as Jenkins, Sonar for developers

Doing this manually is a time consuming task and requires co-ordination in the sequence of service deployment i.e. the services should be deployed and restarted in the specified sequence.

If there are any issues in the deployment, support person or a developer needs to logon to these application servers to debug the issue through logs. If the environment is clustered, then need to logon to each clustered box for the logs. This process is time consuming and requires monitoring on multiple servers.

There is a requirement of some tools in most of the enterprise applications and we spend much time in setting up these servers and maintaining them. Why don’t we build a framework or a solution which fits for all?

# DevOps Suite 
Below are some of the tools integrated as part of DevOps Suite.
1.	ELK: Collects log files from configured services and dumps them on the server. Server then indexes these logs and shows them on the web interface.
2.	OMD: Service monitoring tool which raises alerts if there are any problems on the deployment servers.
3.	Vagrant: Tool to setup VMs required for the services and other servers like Jenkins, Sonar, etc.
4.	Jenkins: Jenkins is a cross-platform, continuous integration and continuous delivery application that increases your productivity. Use Jenkins to build and test your software projects continuously making it easier for developers to integrate changes to the project, and making it easier for users to obtain a fresh build. It also allows you to continuously deliver your software by providing powerful ways to define your build pipelines and integrating with a large number of testing and deployment technologies.
5.	SonarQube: SonarQube is an open platform to manage code quality. As such, it covers the 7 axes of code quality:

6.	Postgresql: Database server
7.	Docker: Docker offers a container-based virtualization platform to power your applications.

# Architecture
Vagrant installed on Windows/Linux machine is responsible for creating the Virtual Machines on Oracle Virtual Box. Checkout the “DevOps_Platform” code on to the local machine which contains vagrant files and shell scripts for setting up the VMs.

### Process & Environment Integration

![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/process_flow.jpg)

### Environment

![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/architecture.jpg)

The “Application Runtime Environment” could be replicated to create different environments like Integration Test environment, CICB, UAT, PTLs and finally Production. Replication of environments has become very easy due to scripts and Docker virtualization.
As shown it the above diagram, we have created a small application related to an organisation. The application is developed using Microservices architecture principles. Each service is responsible for handling a single entity such as Employee, Project and Project assignments for the employees. These services expose RESTful APIs for CRUD operations as well as listings. For performing operations on these entities we have created Organisation Web Application which consists of UI related files.
 
Deployed these services in docker containers to minimize the footprint of application.
##### Execution Process
Once a developer implements code change and commits his code to Git, Jenkins will trigger a job. This job will build the artifacts and generates a sonar report which would be published on SonarQube server. Also, deploys the created artifacts on docker containers which are ready for testing. Database incremental scripts execution can also be automated on the specified environment (which is not in place yet).
On each of the containers in docker we have ELK and OMD agents installed. ELK agent consolidates the log files which are configured on respective containers and sends them to ELK server. ELK server indexes these files and publishes them on Kibana Web. These logs are available to developers, testers and product owners for further analysis. We can filter the logs on the basis of services, timestamps, a string in the logs, hosts, etc.

![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/dashboard_ELK.png)
 
OMD agent consolidates the information about the server and the services running on the containers. This consolidated information is forwarded to OMD server which analyses this data and raises alerts if there are any problems with the services or containers.

![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/dashboard_check_mk.png)

Because of all these tools in place, from the day 1 of the project kick-off we will have the production like infrastructure available.

##### Docker
Docker can be installed on any Linux machine. This provides virtualised containers for application deployments. A single Linux installation and hardware is shared across all the containers which minimises the footprint of the application deployment.
 
![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/docker_deployment.png)
 
Each container is as good as a different VM in its world. Hence the service running on this container can map to any port without worrying about other containers port. Docker port forwarding is responsible for resolving these ports on the container level and assigning different ports which are exposed to outside world or other containers.
 
The built artifacts can be deployed on the docker containers. The containers created can be shipped as is to any environment. Replication of Production environment to Staging or any other environment for reproducing the issues in the application maintenance phase is possible by shipping the containers.
 
##### Microservices Architecture
The organisation application built using Microservices architecture in which each service is responsible for handling only one type of entity. It has Employee Service, Project Service, Project Assignment Service and Organisation Web application. All the services are built using spring boot feature. Hence all the dependencies needed for application deployment has been embedded into the archive file. There is no need to ship tomcat, jboss, etc. onto the containers because jar/war files are self-capable to take the application server up on the specified port.
All the services have uniform RESTful APIs which exposes CRUD operations for Employee, Project and Project Assignment.

![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/organisation_web_app.png)

Additionally we have developed below artifacts which are included in the Microservices so that there is less code duplication as well as less maintenance.
##### devops-commons
This artifact has many features which could be used across all the Microservices.
1.	Generic DAOs for interacting with the database for CRUD operations.
2.	LoggingInterceptor which is implemented using AOP for performance and method start & end logging.
3.	Orika mapper configuration for object conversions.
4.	Exception controller advice handling the exceptions at controller levels across all the Microservices. Hence no need to handle the exceptions at the controller levels.

##### devops-exception-manager
The application exceptions can be wrapped into DefaultWrappedException or SystemWrappedException (Runtime Exception). The messages associated with Exception codes can be configured in exception-message-store.properties which would be resolved while creating the exception object. The exception messages resolved using these properties file can be logged as well as could be shown on the UI to end users directly.
##### devops-logger
This is a wrapper component developed around log4j implementation. The implementation consists of a LoggerFactory which produces DiagnosticLogger, PerformanceLogger and ErrorLogger objects. Instead of collating all the logs in a single log file, these log files will create diagnostic, performance and error log files respectively. This component can be plugged across all the Microservices. The log messages are resolved from the configuration file log-message-store.properties.

The services and UI components are designed & developed in such a way that the replication for other entity types is very easy. Because of Microservices architecture each service would have @100-200 lines of code. Due to this, each service could be maintainable and would be less dependent on other entities.

# Installation
1. Download Cygwin
32-bit Cygwin:
```http://www.redhat.com/services/custom/cygwin/rhsetup-x86.exe```
OR
64-bit Cygwin:
```http://www.redhat.com/services/custom/cygwin/rhsetup-x86_64.exe```
***Note: Install with package dos2unix, SSH.***
2. Download Vagrant and install with default options
```https://releases.hashicorp.com/vagrant/1.7.4/vagrant_1.7.4.msi```
3. Download latest Docker Tool Box and install with the default option 
```https://github.com/docker/toolbox/releases```
4. Create directory ```<Drive:>\<Directory>\git``` e.g. ```C:\DevOps-Suite\git```
5. Open git bash and go to location ```<Drive:>\<Directory>\git```
Clone the repository ```https://github.com/MastekLtd/ DevOps_Platform.git``` using below commands
6. Run the following commands
    ```
    git clone https://github.com/MastekLtd/ DevOps_Platform.git
    cd DevOps_Platform
    git checkout develop
    git pull
    ```
7. Open Red Hat Cygwin Terminal and go to location ```<Drive:>\<Directory>\git \DevOps_Platform```
8. Execute ***setupAllVMs.sh*** to setup DevOps Suite

```./setupAllVMs.sh local use-cloud-image```

***Note: If the above command gives any error then please execute below commands which will replace windows line endings with linux line endings:***

```find . -name \*.sh -exec dos2unix {} \```

and then execute the command ```./setupAllVMs.sh local use-cloud-image``` again to setup DevOps Suite
9. Above script takes some time for execution. Once above execution completes, you can see below result on Redhat Cygwin console by executing the below command:
```vagrant global-status```
![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/vagrant-status.jpg)
10. Open following links for the services running on your virtual machines

***Organisation application:***
```http://192.168.29.110:11990/employee/employees```

***Open Monitoring Distribution with Check_MK:***
```http://192.168.51.105/monitoring```

***Jenkin & Sonar server:***
```
http://192.168.51.104:8080/
http://192.168.51.104:9080/sonar/
```
    
***Postgres Database:***
```Host: 192.168.51.106 and Port 5432```

***ELK:***
```
http://192.168.51.102:9200/_plugin/bigdesk
http://192.168.51.102:9200/_plugin/head/
http://192.168.51.102:5601
```
    
![alt text](https://github.com/MastekLtd/DevOps_Platform/blob/master/images/elk.jpg)
 
Please select @timestamp from the highlighted dropdown menu and then click the Create button for first index creation on the basis of timestamp.
Once the index is created, click the ***Discover*** link in the top navigation bar. By default, this will show you the entire log data which is pulled from different Microservices deployed on Docker containers.
