@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

::--------------------------------------------------------
::-- @author frankdevhub@163.com 
::-- @Date 2019/04/05 Friday
::-- @description: submit renewed post file
::--------------------------------------------------------

@echo 'start' 

git add .
git commit -m "jekyll page rebuild"
git push -u origin master