docker run -p 10000:10000 -p 10001:6556 --name uuid -h uuid -v /mnt/gluster/repo/docker/config/services:/home/devops/config/services -v /mnt/gluster/repo/Build/uuid/:/usr/share/ uuid
