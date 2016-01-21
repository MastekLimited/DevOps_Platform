/*! grafana - v2.5.0 - 2015-10-28
 * Copyright (c) 2015 Torkel Ödegaard; Licensed Apache-2.0 */

define(["settings"],function(a){"use strict";var b=window.grafanaBootData||{settings:{}},c=b.settings;return new a(c)});

datasources: {
jmeter: {
type: 'influxdb',
url: "http://localhost:8086/db/jmeter",
username: 'root',
password: 'root',
}, collectd: {
type: 'influxdb',
url: "http://localhost:8086/db/collectd",
username: 'root',
password: 'root',
}, grafana: {
type: 'influxdb',
url: "http://localhost:8086/db/grafana",
username: 'root',
password: 'root',
grafanaDB: true
},
}, 
