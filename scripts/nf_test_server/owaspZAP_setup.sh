cd /opt
echo ...........................Get OWASP ZAP TAR file............................
wget https://github.com/zaproxy/zaproxy/releases/download/2.4.3/ZAP_2.4.3_Linux.tar.gz

# Untar OWASP ZAP TAR file,and move it under /opt/zaproxy directory
echo ...........................Untar OWASP ZAP TAR file............................
tar -xzvf ZAP_2.4.3_Linux.tar.gz
cd /opt/ZAP_2.4.3
#./zap.sh &
