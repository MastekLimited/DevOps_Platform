# Written by WATO
# encoding: utf-8


if only_hosts == None:
    only_hosts = []

only_hosts = [
  ( ['!offline', ], ALL_HOSTS, {'comment': u'Do not monitor hosts with the tag "offline"'} ),
] + only_hosts


agent_ports = [
  ( 9087, ['cmk-agent', ], ['EMP'] ),
  ( 9088, ['cmk-agent', ], ['PROJECT'] ),
  ( 9093, ['cmk-agent', ], ['WEB'] ),
  ( 6556, ['cmk-agent', ], ['OMD_SERVER'] ),
] + agent_ports


host_contactgroups = [
  ( 'DevOps', [], ['EMP', 'OMD_SERVER', 'PROJECT'], {'comment': u'Put all hosts into the contact group "all"'} ),
] + host_contactgroups


bulkwalk_hosts = [
  ( ['!snmp-v1', ], ALL_HOSTS, {'comment': u'Hosts with the tag "snmp-v1" must not use bulkwalk'} ),
] + bulkwalk_hosts


ping_levels = [
  ( {'loss': (80.0, 100.0), 'packets': 6, 'timeout': 20, 'rta': (1500.0, 3000.0)}, ['wan', ], ALL_HOSTS, {'comment': u'Allow longer round trip times when pinging WAN hosts'} ),
] + ping_levels


active_checks.setdefault('tcp', [])

active_checks['tcp'] = [
  ( (8087, {'response_time': (100.0, 200.0)}), [], ['EMP'] ),
  ( (8093, {'response_time': (100.0, 200.0)}), [], ['WEB'] ),
  ( (8088, {'response_time': (100.0, 200.0)}), [], ['PROJECT'] ),
] + active_checks['tcp']

