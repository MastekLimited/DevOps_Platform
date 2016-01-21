docker run -p 8087:8087 -p 9087:6556 --name employee -h employee -v /vshare/docker/config/services:/home/devops/config/services -v /vshare/Build/employee/:/usr/share/ employee
