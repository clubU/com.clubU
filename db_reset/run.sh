#!/bin/bash
if [ -z "`uname --all | grep 'win'`" ];
then
	java -cp .:jdbc.jar:csv.jar Main
else
	java -cp '.;jdbc.jar;csv.jar' Main
fi