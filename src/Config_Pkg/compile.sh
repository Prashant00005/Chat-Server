#Script for compiling the java code as mentioned in question requirement
#!/bin/bash
cd '../../src/com/scalablecomputing/server/'
cd '../../src/CS/'
pwd
javac -cp -sourcepath *.java   #compiles java code
