#!/bin/bash

if [ -z "`uname --all | grep 'win'`" ];
then
	javac -cp jdbc.jar:csv.jar Main.java
else
	javac -cp 'jdbc.jar;csv.jar' Main.java
fi
