#/bin/bash
echo ""
echo "Vagrant instances on this machine before destroying:"
echo ""
vagrant global-status
vagrant global-status | for vagrantMachineId in $(awk '/running/{print $1}')
do echo "Destroying vagrant machine:-  $vagrantMachineId"
vagrant destroy -f  $vagrantMachineId
done;
echo ""
echo "Vagrant instances on this machine after destroying:"
echo ""
vagrant global-status