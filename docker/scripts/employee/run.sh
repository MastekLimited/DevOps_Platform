docker run -p 10010:10010 -p 10011:6556 --name employee -h employee -v /vshare/docker/config/services:/home/devops/config/services -v /vshare/Build/employee/:/usr/share/ employee
