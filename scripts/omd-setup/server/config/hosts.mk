# Written by WATO
# encoding: utf-8

all_hosts += [
  "UUID|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Employee|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Project|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Project-Assignment|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Device-Registration|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Device-Authentication|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "Organisation-Web|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
  "OMD_SERVER|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
]

# Explicit IP addresses
ipaddresses.update({'UUID': u'&&DOCKER_HOST_IP&&',
 'Employee': u'&&DOCKER_HOST_IP&&',
 'Project': u'&&DOCKER_HOST_IP&&',
 'Project-Assignment': u'&&DOCKER_HOST_IP&&',
 'Device-Registration': u'&&DOCKER_HOST_IP&&',
 'Device-Authentication': u'&&DOCKER_HOST_IP&&',
 'Organisation-Web': u'&&DOCKER_HOST_IP&&',
 'OMD_SERVER': u'127.0.0.1'})


# Host attributes (needed for WATO)
host_attributes.update(
{'UUID': {'ipaddress': u'&&DOCKER_HOST_IP&&',
         'tag_agent': 'cmk-agent',
         'tag_networking': 'wan'},
'Employee': {'ipaddress': u'&&DOCKER_HOST_IP&&',
         'tag_agent': 'cmk-agent',
         'tag_networking': 'wan'},
 'Project': {'ipaddress': u'&&DOCKER_HOST_IP&&',
             'tag_agent': 'cmk-agent',
             'tag_networking': 'wan'},
 'Project-Assignment': {'ipaddress': u'&&DOCKER_HOST_IP&&',
             'tag_agent': 'cmk-agent',
             'tag_networking': 'wan'},
 'Device-Registration': {'ipaddress': u'&&DOCKER_HOST_IP&&',
             'tag_agent': 'cmk-agent',
             'tag_networking': 'wan'},
 'Device-Authentication': {'ipaddress': u'&&DOCKER_HOST_IP&&',
             'tag_agent': 'cmk-agent',
             'tag_networking': 'wan'},
 'Organisation-Web': {'ipaddress': u'&&DOCKER_HOST_IP&&',
         'tag_agent': 'cmk-agent',
         'tag_networking': 'wan'},
 'OMD_SERVER': {'ipaddress': u'127.0.0.1',
                'tag_agent': 'cmk-agent',
                'tag_networking': 'wan'}})
