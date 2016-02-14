#copy logstash-forwarder file to elk agent directory.
cp /mnt/gluster/repo/certificates/generated/logstash-forwarder.crt /mnt/gluster/repo/docker/roles/base/elk-setup/agent/config/

if [ -f "/vshare/base-images/docker/centos-6.6" ]; then
	docker load -i /vshare/base-images/docker/centos-6.6
fi

#build docker base image.
docker build --no-cache -t base roles/base