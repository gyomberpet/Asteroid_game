@ECHO OFF
if not exist .\class mkdir .\class
dir /s /B *.java > sources.txt
javac -d .\class @sources.txt
java -cp .\class Program
Pause