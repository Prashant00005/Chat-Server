# Chat-Server

Name: Prashant Aggarwal
Email: aggarwap@tcd.ie
Id: 17317559

This readme file contains how to run and configure the chat-server.
There are two shell files that needs to run in order to activate the server.
compile.sh
run.sh

Steps to follow:

Go to Chat-Server/src/Config_Pkg/ and run the following command:

dos2unix compile.sh
dos2unix start.sh

Open start.sh file (vi start.sh) and put the ip address of the server on line 4 Server_Ip = 'here'. Save and return back to terminal.

Now, go to Chat-Server/src/Config_Pkg/ and run the following command:
sh compile.sh
Server Started (output from compile.sh file)

Go to Chat-Server/src/Config_Pkg/ and run the following command:
sh start.sh
Server compiled (output from start.sh file)

Dependency:
Java 1.8
dos2unix
