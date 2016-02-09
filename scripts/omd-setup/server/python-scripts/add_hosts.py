#!/usr/bin/python
import os
import sys
try:
    path = os.environ.pop('OMD_ROOT')
    pathlocal = "~/etc/check_mk/conf.d/wato"
    makehostmkfile="~/etc/check_mk/conf.d/wato/hosts.mk"
    pathlocal = os.path.expanduser(pathlocal)
    datei = open(sys.argv[1],'r') 
except:
    print """Run this script inside a OMD site
    Usage: ./addhost_import.py csvfile.csv
    CSV Example:
    wato_foldername;hostname;host_alias;ipaddress|None"""
    sys.exit()

folders = {}
print """......Reading CSV file......"""
for line in datei:
    ordner, name, alias, ipaddress = line.split(';')[:4]
    if ordner:
       # try:
           # os.makedirs(pathlocal+ordner)
       # except os.error:
        #    pass
        folders.setdefault(ordner,[])
        ipaddress = ipaddress.strip()
        if ipaddress == "None":
            ipaddress = False 
        folders[ordner].append((name,alias,ipaddress))
datei.close()

print """Start host.mk file writing"""
for folder in folders:
    all_hosts = "" 
    host_attributes = "" 
    ips = ""
    for name, alias, ipaddress in folders[folder]:
        all_hosts += "'%s',\n" % (name)
        if ipaddress:
            host_attributes += "'%s' : {'alias' : u'%s', 'ipaddress' : '%s' },\n" % (name, alias, ipaddress)
            ips += "'%s' : '%s'," % ( name, ipaddress )
        else:
            host_attributes += "'%s' : {'alias' : u'%s' },\n" % (name, alias)

	

    ziel = open(pathlocal + '/hosts.mk','w') 
    ziel.write('all_hosts += [')
    ziel.write(all_hosts)
    ziel.write(']\n\n')
    ziel.write('host_attributes.update({')
    ziel.write(host_attributes)
    ziel.write('})\n\n')
    if len(ips) > 0:
        ziel.write('ipaddresses.update({')
        ziel.write(ips)
        ziel.write('})\n\n')
    ziel.close()
