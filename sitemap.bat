
@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/03 Wednesday
::-- @description: generate sitemap and robot.txt
::--------------------------------------------------------

set "current_log_timestamp= "

@echo 'start' 

::--------------------------------------------------------
::-- @description: install plugin jekyll-sitemap
::--------------------------------------------------------

gem install jekyll-sitemap

jekyll build & echo "jekyll build complete" 
call:replceHostConfig

git add .
git commit -m "generate sitemap.xml and robot.txt and push to origin"
git push -u origin master

::--------------------------------------------------------
::-- Function section starts below here
::--------------------------------------------------------

:getLocalTimeStamp 

	set date_timestamp_format=%1
	set time_timestamp_format=%2
	
	if date_timestamp_format==1 (
		set current_log_timestamp=%DATE:~4,4%%DATE:~9,2%%DATE:~12,2%
	) else (
		set current_log_timestamp=%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
	)

GOTO:EOF



:replceHostConfig

set "root=%~dp0"
echo "current_path:%root%"

set "site_file_sitemap=\_site\sitemap.xml"
set "site_file_robot=\_site\robots.txt"
set "root_file_sitemap=sitemap.xml"
set "root_robot=robot.txt"

set  source_sitemap_path=%root%%site_file_sitemap%

set  source_robot_path=%root%%site_file_robot%

:: (
::    for /f "tokens=*" %%i in (%site_file_sitemap%) do (
::        set s=%%i
::        set s=!s:localhost:4000=www.frankdevhub.site!
::        echo !s!
::    )
:: )>%root_file_sitemap%


copy  %source_sitemap_path% %root%  
copy  %source_robot_path% %root%
echo '---------set sitemap configuations complete---------'
GOTO:EOF

echo error



PAUSE