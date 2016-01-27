docker run -p 10010:10010 -p 10011:6556 --name employee -h employee -v /mnt/devops/config/services:/home/devops/config/services -v /mnt/devops/Build/employee/:/usr/share/ employee
