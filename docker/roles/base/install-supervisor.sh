#!/bin/bash
installablesDirectory=$1
workingDirectory=$(pwd)
supervisorInstallablesDirectory=$installablesDirectory/misc/supervisor

echo '================================================================================'
echo '			Installing Unzip'
echo '================================================================================'
if [ -f "$installablesDirectory/misc/unzip-6.0-13.el7.x86_64.rpm" ]; then
	yum install -y $installablesDirectory/misc/unzip-6.0-13.el7.x86_64.rpm
else
	yum install -y unzip
fi

cd $supervisorInstallablesDirectory

echo '================================================================================'
echo '			Installing Supervisor: START'
echo '================================================================================'
if [ -f "setuptools-20.2.zip" ] &&
	[ -f "meld3-0.6.5.tar.gz" ] &&
	[ -f "elementtree-1.2.6-20050316.tar.gz" ] &&
	[ -f "supervisor-3.2.1.tar.gz" ]; then

	unzip setuptools-20.2.zip;
	tar xf meld3-0.6.5.tar.gz;
	tar xf elementtree-1.2.6-20050316.tar.gz;
	tar xf supervisor-3.2.1.tar.gz;

	cd setuptools-20.2; python setup.py install; cd ..;pwd;
	cd meld3-0.6.5; python setup.py install; cd ..;
	cd elementtree-1.2.6-20050316; python setup.py install; cd ..;
	cd supervisor-3.2.1; python setup.py install; cd ..;

	rm -R setuptools-20.2;
	rm -R meld3-0.6.5;
	rm -R elementtree-1.2.6-20050316;
	rm -R supervisor-3.2.1;

	cd $workingDirectory
else
	easy_install supervisor;
fi

echo '================================================================================'
echo '			Installing Supervisor: END'
echo '================================================================================'
exit