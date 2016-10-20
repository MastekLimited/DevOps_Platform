#!/usr/bin/expect -f
#This script will change database password

-u postgres psql postgres
send "\password postgres"
send "Password1"
send "Password1"
send "\q"
