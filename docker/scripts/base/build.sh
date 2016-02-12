#copy logstash-forwarder file to elk agent directory.
/vshare/certificates/generated/logstash-forwarder.crt /vshare/docker/roles/base/elk-setup/agent/config/

#build docker base image.
docker build --no-cache -t base roles/base