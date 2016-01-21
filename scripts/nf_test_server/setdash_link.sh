#!/bin/bash

cd /usr/share/grafana
# backup
ls public/app/app.*.js | xargs -I{} cp {} {}.bak
# apply change
sed -i 's|\({text:"Dashboards",icon:"fa fa-fw fa-th-large",href:a.getUrl("/")}\)|\1,{text:"Servers Stats",icon:"fa fa-fw fa-th-large",href:a.getUrl("/dashboard/script/getdash.js")}|' public/app/app.*.js

sed -i 's|\({text:"Dashboards",icon:"fa fa-fw fa-th-large",href:a.getUrl("/")}\)|\1,{text:"NF Test Stats",icon:"fa fa-fw fa-th-large",href:a.getUrl("/dashboard/script/getnfdash.js")}|' public/app/app.*.js

service grafana-server restart