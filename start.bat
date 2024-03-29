@echo off

set JAVA_HOME="C:\java"
set DB_URL=jdbc:oracle:thin:@//192.168.0.1:1521/test
set DB_USER=user
set DB_PASS=pass
set DB_CHARSET=SJIS


set JAVA_OPTS=-Xms64M -Xmx64M
set CURRENT=%~dp0
set MAIN=org.crudtest.gui.Start
set LOG_FILE=%CURRENT%\app.log
set LOG_LEVEL=WARN

set OPTS=-Ddatabase.url=%DB_URL%^
 -Ddatabase.user=%DB_USER%^
 -Ddatabase.pass=%DB_PASS%^
 -Ddatabase.charset=%DB_CHARSET%^
 -Dlogfile.path=%LOG_FILE%^
 -Dlog.level=%LOG_LEVEL%

set CLASS_PATH=%CURRENT%\lib\*

cd %CURRENT%
start /b "" ""%JAVA_HOME%\bin\javaw.exe"" %JAVA_OPTS% %OPTS% -cp %CLASS_PATH% %MAIN%

endlocal
exit
