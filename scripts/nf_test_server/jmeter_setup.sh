#!/bin/bash

# Environment Veriable
JMETER_VERSION=2.13
PLUGINS_VERSION=1.2.0
JMETER_PATH=/home/vagrant/jmeter
PLUGINS_PATH=$JMETER_PATH/plugins

# Install Jmeter

echo ...........................Installing Jmeter Package............................
 sudo mkdir -p $JMETER_PATH && cd $JMETER_PATH
	sudo wget http://www.eu.apache.org/dist//jmeter/binaries/apache-jmeter-$JMETER_VERSION.tgz
    sudo tar -zxf apache-jmeter-$JMETER_VERSION.tgz
    sudo rm apache-jmeter-$JMETER_VERSION.tgz

# Install dependencies
# - JMeterPlugins-Standard 1.2.0
# - JMeterPlugins-Extras 1.2.0
# - JMeterPlugins-ExtrasLibs 1.2.0

# Install JMeterPlugins-ExtrasLibs
echo ...........................Installing Jmeter Plugins............................
 sudo mkdir -p $PLUGINS_PATH
    sudo wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-ExtrasLibs-$PLUGINS_VERSION.zip
    sudo unzip -o -d $PLUGINS_PATH JMeterPlugins-ExtrasLibs-$PLUGINS_VERSION.zip
    sudo wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-Extras-$PLUGINS_VERSION.zip
    sudo unzip -o -d $PLUGINS_PATH JMeterPlugins-Extras-$PLUGINS_VERSION.zip && \
    sudo wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-Standard-$PLUGINS_VERSION.zip
    sudo unzip -o -d $PLUGINS_PATH JMeterPlugins-Standard-$PLUGINS_VERSION.zip

# Copy plugins to jmeter enviroment
sudo cp $PLUGINS_PATH/lib/*.jar $JMETER_PATH/apache-jmeter-$JMETER_VERSION/lib/
sudo cp $PLUGINS_PATH/lib/ext/*.jar $JMETER_PATH/apache-jmeter-$JMETER_VERSION/lib/ext/

sudo cp sudo cp /mnt/gluster/repo/devops_v1.0.jmx /home/vagrant/jmeter/apache-jmeter-2.13/bin/templates/
# Install xorg

#echo ...........................Installing xorg Package............................
#sudo yum install -y openssh-server xorg-x11-xauth xorg-x11-apps


# Install Firefox

#echo ...........................Installing Firefox Package............................
# Install Firefox
#sudo yum install -y firefox