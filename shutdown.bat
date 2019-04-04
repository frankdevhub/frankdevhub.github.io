@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/05 Friday
::-- @description: shutdown local service
::--------------------------------------------------------

@echo 'start' 

for /f "tokens=5" %%i in ('netstat -aon ^| findstr ":4000"') do (
    set n=%%i
)

if not "%n%"!=="" (
   taskkill /f /pid %n%
) else (
   echo "no existing running service on  default 4000"
) 


PAUSE

