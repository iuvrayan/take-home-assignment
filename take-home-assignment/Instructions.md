Prerequisites:
==============
1) Make sure that jdk-11.0.1 is installed.
2) Make sure that maven is installed.

Build:
======
1) Go the directory "take-home-assignment"
2) Run the command "mvn clean install"

Run:
====
1) Move to directory "service" and run the command "java -jar target/service-0.0.1-SNAPSHOT.jar" to start the Spring Boot Tomcat Server
2) Open another terminal / prompt and run the command " java -jar target/service-0.0.1-SNAPSHOT-jar-with-dependencies.jar" to start the gRPC Server

Execute:
========
1) Use the URL "http://localhost:8080/marionete/useraccount/" with POST paloads like {"username":"bla", "password":"pwd1"} and  {"username":"regi", "password":"pwd2"}