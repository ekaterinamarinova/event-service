#!/bin/bash
#a shell script that:
#1. runs a mvn clean install on the event-service project
#2. enters the karaf console
#3. runs the uninstall and installs feature:events command
echo "Running mvn clean install in directory: " $(pwd)
#mvn clean install
echo "Entering Karaf Home"
cd ../apache-karaf-4.1.3/bin
echo "Entered: " $(pwd)
echo "Running karaf script: "
./karaf
