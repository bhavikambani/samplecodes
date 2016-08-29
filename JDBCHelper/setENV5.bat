@ ECho off

Echo Setting Environment....

SET OLDPATH=%PATH%
SET CLASSPATH=
SET JAVA_HOME=D:\Environment\jdk1.5.0_01
SET ANT_HOME=D:\Environment\apache-ant-1.6.0
REM SET TOMCAT_HOME=D:\Environment\jakarta-tomcat-3.3.2
REM SET TOMCAT_HOME=D:\Environment\jakarta-tomcat-5.0.28
SET TOMCAT_HOME=D:\Environment\24online_tomcatv5
SET PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%OLDPATH%
set ANT_OPTS=-Xms64m -Xmx128m
Echo. 
Echo PATH: %PATH%
Echo. 
java -version
Echo.
Echo ANT calling
%ANT_HOME%\bin\ant -version