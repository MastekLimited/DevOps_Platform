adduser devops
echo -e "Password1\nPassword1" | (passwd --stdin devops)
echo "devops ALL=(ALL)	ALL" >> /etc/sudoers