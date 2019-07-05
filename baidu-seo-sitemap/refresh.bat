@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/05 Friday
::-- @description: submit renewed post file
::--------------------------------------------------------

@echo 'start' 

git pull
git add .
git commit -m "update baidu seo utils"

git push

PAUSE