#Script that would run the implemented server
#!/bin/bash
echo Server Started
Server_Ip=192.168.43.133
Server_Port=$1   #Takes port number as command line argument
cd "../../src/"
java CS.ChatServer $Server_Port $Server_Ip
