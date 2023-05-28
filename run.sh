#!/bin/bash

#home
TOM_HOME="/home/nadimmahmud/Nadim/apache-tomcat-9.0.72"

#server uri
URI="http://localhost:8081/"

#copy war to tomcat
cp ./build/libs/*.war $TOM_HOME/webapps/

#runtomcat
$TOM_HOME/bin/startup.sh

sleep 3

#remove previous app
if [ -d $TOM_HOME/webapps/ROOT/ ]; then
	rm -r $TOM_HOME/webapps/ROOT/
	echo "deleting root"
fi

for file in ./build/libs/*.war; do
	file_name=$(basename "$file" .war)
	
	echo $file_name
	
	#wait while extraction is complete
	while [ ! -d "$TOM_HOME/webapps/$file_name" ]; do
    	sleep 1
	done
	
	mv $TOM_HOME/webapps/$file_name $TOM_HOME/webapps/ROOT/
done

sleep 2

#open uri on defaul browser
xdg-open "$URI"

