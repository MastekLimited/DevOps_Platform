#!/bin/bash
HOST=$1
USER="pi"
PASSWORD="raspberry"
COMMAND=$2

VAR=$(expect -c "
spawn ssh -o StrictHostKeyChecking=no $USER@$HOST $COMMAND
match_max 100000
expect \"*?assword:*\"
send -- \"$PASSWORD\r\"
send -- \"\r\"
set timeout 300
expect eof
")
echo "=======Successfully executed command:$COMMAND========"