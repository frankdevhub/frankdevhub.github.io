
::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/03 Wednesday
::-- @description: generate sitemap and robot.txt
::--------------------------------------------------------

set current_log_timestamp=""
:getLocalTimeStamp

set date_timestamp_format=%1
set time_timestamp_format=%2

if date_timestamp_format==1
current_log_timestamp=%DATE:~4,4%%DATE:~9,2%%DATE:~12,2%

if time_timestamp_format==1
current_log_timestamp=%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%

goto:eof

@echo 'start at call:getLocalTimeStamp 1 -1' 

::--------------------------------------------------------
::-- @description: install plugin jekyll-sitemap
::--------------------------------------------------------

@echo off
setlocal enabledelayedexpansion


jekyll build &echo 'sss' 




& pause