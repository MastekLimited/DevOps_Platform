#copy logstash-forwarder file to elk agent directory.
cp /mnt/gluster/repo/certificates/generated/logstash-forwarder.crt /mnt/gluster/repo/docker/roles/base/elk-setup/agent/config/

if [ -f "/vshare/base-images/docker/centos-6.6" ]; then
	docker load -i /vshare/base-images/docker/centos-6.6
fi

echo ...........................Copying installables to docker.roles.base.installables...........................

mkdir /mnt/gluster/repo/docker/roles/base/installables/
cp /vshare/base-images/misc /mnt/gluster/repo/docker/roles/base/installables/
cp /vshare/base-images/jdk /mnt/gluster/repo/docker/roles/base/installables/

mkdir /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/
cp /vshare/base-images/elk/GPG-KEY-elasticsearch /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/
cp /vshare/base-images/elk/logstash-forwarder-0.4.0-1.x86_64.rpm /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/

mkdir /mnt/gluster/repo/docker/roles/base/omd-setup/agent/installables/
cp /vshare/base-images/elk/GPG-KEY-elasticsearch /mnt/gluster/repo/docker/roles/base/omd-setup/agent/installables/
cp /vshare/base-images/elk/logstash-forwarder-0.4.0-1.x86_64.rpm /mnt/gluster/repo/docker/roles/base/omd-setup/agent/installables/

#build docker base image.
docker build --no-cache -t base roles/base