#copy logstash-forwarder file to elk agent directory.
cp /vshare/certificates/generated/logstash-forwarder.crt /mnt/gluster/repo/docker/roles/base/elk-setup/agent/config/

#build docker base image.
docker build --no-cache -t base roles/base