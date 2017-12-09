# Chat-Server

Name: Prashant Aggarwal<br>
Email: aggarwap@tcd.ie<br>
Id: 17317559<br>

This readme file contains how to run and configure the chat-server.<br>
There are two shell files that needs to run in order to activate the server.<br>
compile.sh<br>
run.sh<br>

# Steps to follow:<br>

Go to Chat-Server/src/Config_Pkg/ and run the following command:<br>

dos2unix compile.sh<br>
dos2unix start.sh<br>

Open start.sh file (vi start.sh) in the terminal and put the ip address of the server on line 4 Server_Ip = 'here'. Save and return back to terminal.

Now, go to Chat-Server/src/Config_Pkg/ and run the following command:<br>
sh compile.sh<br>
Server Compiled (output from compile.sh file)<br>

Go to Chat-Server/src/Config_Pkg/ and run the following command:<br>
sh start.sh<br>
Server Started (output from start.sh file)<br>

# Dependencies:
Java 1.8<br>
dos2unix
