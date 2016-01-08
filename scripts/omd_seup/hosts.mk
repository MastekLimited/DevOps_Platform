# Written by WATO
# encoding: utf-8

all_hosts += [
  "EMP|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "OMD_SERVER|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "PROJECT|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "WEB|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
]

# Explicit IP addresses
ipaddresses.update({'EMP': u'192.168.29.110',
 'OMD_SERVER': u'127.0.0.1',
 'PROJECT': u'192.168.29.110',
 'WEB': u'192.168.29.110'})


# Host attributes (needed for WATO)
host_attributes.update(
{'EMP': {'ipaddress': u'192.168.29.110',
         'tag_agent': 'cmk-agent',
         'tag_networking': 'wan'},
 'OMD_SERVER': {'ipaddress': u'127.0.0.1',
                'tag_agent': 'cmk-agent',
                'tag_networking': 'wan'},
 'PROJECT': {'ipaddress': u'192.168.29.110',
             'tag_agent': 'cmk-agent',
             'tag_networking': 'wan'},
 'WEB': {'ipaddress': u'192.168.29.110',
         'tag_agent': 'cmk-agent',
         'tag_networking': 'wan'}})
