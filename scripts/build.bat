@echo off
del /S /Q ..\res\*.class >nul
dir /B /S /O:N ..\src\csheets\*.java >c.lst
javac -cp ../src;../lib/antlr.jar;../dist/lib/derbyclient.jar;../dist/lib/mysql-connector-java-5.1.20-bin.jar/;../dist/lib/postgresql-9.1-902.jdbc4.jar/;../dist/lib/sqljdbc4.jar/;../dist/lib/ejb3-persistence.jar;../dist/lib/hibernate3.jar;../dist/lib/*.jar;../lib/derbyclient.jar;../lib/ejb3-persistence.jar;../lib/hibernate3.jar;../lib/*.jar;../src/csheets/io/*.java;../src/hibernate.cfg.xml;../dist/lib/hibernate-annotations.jar;../lib/hibernate-annotations.jar -d ../res @c.lst %1 %2 %3
del c.lst