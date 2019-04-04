@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/05 Friday
::-- @description: build and start local service
::-- @host: http://localhost:4000
::--------------------------------------------------------

@echo 'start' 

for /f "tokens=5" %%i in ('netstat -aon ^| findstr ":4000"') do (
    set n=%%i
)

if not "%n%"!=="" (
   taskkill /f /pid %n%
) 

jekyll build | jekyll serve  


PAUSE

