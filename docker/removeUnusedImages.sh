echo 'Removing unused images'
for OUTPUT in $(docker images | grep '^<none>' | awk '{print $3}')
do 
	docker rmi -f $OUTPUT
done