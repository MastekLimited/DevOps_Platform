#copy logstash-forwarder file to elk agent directory.
cp /mnt/gluster/repo/certificates/generated/logstash-forwarder.crt /mnt/gluster/repo/docker/roles/base/elk-setup/agent/config/

if [ -f "/vshare/repo/docker/centos-7" ]; then
	docker load -i /vshare/repo/docker/centos-7
	echo "Found centos-7"
fi

echo '================================================================================'
echo '			Copying installables to docker.roles.base'
echo '================================================================================'

mkdir /mnt/gluster/repo/docker/roles/base/installables/
cp -R /vshare/repo/misc /mnt/gluster/repo/docker/roles/base/installables/
cp -R /vshare/repo/java /mnt/gluster/repo/docker/roles/base/installables/

mkdir /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/
cp /vshare/repo/elk/GPG-KEY-elasticsearch /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/
cp /vshare/repo/elk/logstash-forwarder-0.4.0-1.x86_64.rpm /mnt/gluster/repo/docker/roles/base/elk-setup/agent/installables/

mkdir /mnt/gluster/repo/docker/roles/base/omd-setup/agent/installables/
cp -R /vshare/repo/check_mk/agent/* /mnt/gluster/repo/docker/roles/base/omd-setup/agent/installables/
chmod -R 777 /mnt/gluster/repo/docker/roles/base/

#build docker base image.
docker build --no-cache -t base roles/base