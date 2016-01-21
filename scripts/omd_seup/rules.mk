# Written by WATO
# encoding: utf-8


if only_hosts == None:
    only_hosts = []

only_hosts = [
  ( ['!offline', ], ALL_HOSTS, {'comment': u'Do not monitor hosts with the tag "offline"'} ),
] + only_hosts


agent_ports = [
  ( 10001, ['cmk-agent', ], ['UUID'] ),
  ( 9087, ['cmk-agent', ], ['Employee'] ),
  ( 9088, ['cmk-agent', ], ['Project'] ),
  ( 9092, ['cmk-agent', ], ['Project-Assignment'] ),
  ( 9094, ['cmk-agent', ], ['Device-Registration'] ),
  ( 9095, ['cmk-agent', ], ['Device-Authentication'] ),
  ( 9093, ['cmk-agent', ], ['Organisation-Web'] ),
  ( 6556, ['cmk-agent', ], ['OMD_SERVER'] ),
] + agent_ports


host_contactgroups = [
  ( 'DevOps', [], ['UUID', 'Employee', 'Project', 'Project-Assignment', 'Device-Registration', 'Device-Authentication', 'Organisation-Web', 'OMD_SERVER'], {'comment': u'Put all hosts into the contact group "all"'} ),
] + host_contactgroups


bulkwalk_hosts = [
  ( ['!snmp-v1', ], ALL_HOSTS, {'comment': u'Hosts with the tag "snmp-v1" must not use bulkwalk'} ),
] + bulkwalk_hosts


ping_levels = [
  ( {'loss': (80.0, 100.0), 'packets': 6, 'timeout': 20, 'rta': (1500.0, 3000.0)}, ['wan', ], ALL_HOSTS, {'comment': u'Allow longer round trip times when pinging WAN hosts'} ),
] + ping_levels


active_checks.setdefault('tcp', [])

active_checks['tcp'] = [
  ( (10000, {'response_time': (100.0, 200.0)}), [], ['UUID'] ),
  ( (8087, {'response_time': (100.0, 200.0)}), [], ['Employee'] ),
  ( (8088, {'response_time': (100.0, 200.0)}), [], ['Project'] ),
  ( (8092, {'response_time': (100.0, 200.0)}), [], ['Project-Assignment'] ),
  ( (8094, {'response_time': (100.0, 200.0)}), [], ['Device-Registration'] ),
  ( (8095, {'response_time': (100.0, 200.0)}), [], ['Device-Authentication'] ),
  ( (8093, {'response_time': (100.0, 200.0)}), [], ['Organisation-Web'] ),
] + active_checks['tcp']

