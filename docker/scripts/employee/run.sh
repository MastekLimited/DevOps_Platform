docker run -p 10010:10010 -p 10011:6556 --name employee -h employee -v /mnt/gluster/repo/docker/config/services:/home/devops/config/services -v /mnt/gluster/repo/Build/employee/:/usr/share/ employee
