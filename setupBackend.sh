#!/bin/bash
echo \
"remember to do this first:
1) create clubu schema
2) setup solr environment path
" && \
cd backend && \
echo `pwd` && \
mvn clean package && \
cd .. && \
if [ -z "`uname --all | grep 'win'`" ];
then
	solr stop -all;
	solr start -s solr;
else
	netstat -a -n -o | grep "8983" | awk 'NR==1{print "taskkill /F /PID "$5}' | sed $'s/\r//' | sh;
	solr.cmd start -s solr;
fi && \
cd db_reset && \
./compile.sh && \
./run.sh && \
echo \
"




----------------------------------------COMMANDS--------------------------------
cd to backend folder
migrate data base:
java -jar target/server-1.0-SNAPSHOT.jar db migrate config.yaml
start server with:
java -jar target/server-1.0-SNAPSHOT.jar server config.yaml

cd to client folder
start ionic app:
ionic server --lab
";