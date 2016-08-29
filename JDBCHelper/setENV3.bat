@ ECho off

Echo Setting Environment....

SET OLDPATH=%PATH%
SET CLASSPATH=
SET JAVA_HOME=D:\Environment\jdk1.3.0
SET ANT_HOME=D:\Environment\apache-ant-1.3

SET TOMCAT_HOME=D:\Environment\jakarta-tomcat-3.3.2
SET PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;

Echo. 
Echo PATH: %PATH%
Echo. 
java -version
Echo.
Echo ANT calling
%ANT_HOME%\bin\ant -version