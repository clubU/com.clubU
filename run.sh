#!/bin/bash
java -jar ./backend/target/server-1.0-SNAPSHOT.jar server ./backend/config.yaml &
cd client
ionic serve