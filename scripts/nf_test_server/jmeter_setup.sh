#!/bin/bash

# Environment Veriable
JMETER_VERSION=2.13
PLUGINS_VERSION=1.2.0
JMETER_PATH=/opt/jmeter
PLUGINS_PATH=$JMETER_PATH/plugins
echo "Installing unzip"
yum install -y unzip


# Install Jmeter

echo ...........................Installing Jmeter Package............................
  mkdir -p $JMETER_PATH && cd $JMETER_PATH
	wget http://www.eu.apache.org/dist//jmeter/binaries/apache-jmeter-$JMETER_VERSION.tgz
    tar -zxf apache-jmeter-$JMETER_VERSION.tgz
    rm apache-jmeter-$JMETER_VERSION.tgz

# Install dependencies
# - JMeterPlugins-Standard 1.2.0
# - JMeterPlugins-Extras 1.2.0
# - JMeterPlugins-ExtrasLibs 1.2.0

# Install JMeterPlugins-ExtrasLibs
echo ...........................Installing Jmeter Plugins............................
  mkdir -p $PLUGINS_PATH
     wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-ExtrasLibs-$PLUGINS_VERSION.zip
     unzip -o -d $PLUGINS_PATH JMeterPlugins-ExtrasLibs-$PLUGINS_VERSION.zip
     wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-Extras-$PLUGINS_VERSION.zip
     unzip -o -d $PLUGINS_PATH JMeterPlugins-Extras-$PLUGINS_VERSION.zip && \
     wget -q http://jmeter-plugins.org/downloads/file/JMeterPlugins-Standard-$PLUGINS_VERSION.zip
     unzip -o -d $PLUGINS_PATH JMeterPlugins-Standard-$PLUGINS_VERSION.zip

# Copy plugins to jmeter enviroment
cp $PLUGINS_PATH/lib/*.jar $JMETER_PATH/apache-jmeter-$JMETER_VERSION/lib/
cp $PLUGINS_PATH/lib/ext/*.jar $JMETER_PATH/apache-jmeter-$JMETER_VERSION/lib/ext/

cp /mnt/gluster/repo/devops_v1.0.jmx /opt/jmeter/apache-jmeter-2.13/bin/templates/
# Install xorg

#echo ...........................Installing xorg Package............................
#sudo yum install -y openssh-server xorg-x11-xauth xorg-x11-apps


# Install Firefox

#echo ...........................Installing Firefox Package............................
# Install Firefox
#sudo yum install -y firefox