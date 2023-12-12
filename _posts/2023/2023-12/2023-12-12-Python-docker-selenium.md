---
layout: post
title:  "Python使用selenium操作Docker里的Chrome浏览器的技巧"
categories: Selenium Python Docker
tags:  Selenium Python
author: Franklinfang
---

* content
{:toc}

## Python使用selenium操作Docker容器中的Chrome浏览器

### 1. 介绍
selenium [官网链接](https://www.selenium.dev/)是我们开展web ui `自动化测试`的利器，可以很方便的用代码模拟人工在浏览器上的操作，实现 `BDD（Behavior-driven development）`，节约大量的人力。
然而，selenium在实际使用又有些痛处：

- 往往需要在 windows 主机上安装、运行浏览器（比如chrome），甚至同时安装多种浏览器，不利于在 Linux 命令行下自动化执行
- 浏览器版本难以管理，回退到特定的版本比较困难
- 持续集成时需要配置 windows 节点机
- 需要为每种浏览器配置 webdriver

所以我们期望有这样的特点：

- 在 Linux 命令行下启动 headless 无界面浏览器
- 方便启动和销毁特定版本的浏览器
- 一体化，减少多个服务的安装配置
- 自带 webdriver
docker-selenium 项目实现了上述这些特点。

### 2. selenium/standalone-chrome
这是一个单机版本的镜像，这个里面包括一个chrome浏览器，可以用来调试selenium程序。

#### 2.1 拉取镜像
```shell
selenium/standalone-chrome
```
#### 2.2 启动容器
chromedriver的默认端口是4444

```shell
docker run -tid --name selenium-standalone-chrome -h selenium-standalone-chrome --memory 1g --memory-swap -1 -p 9515:4444 selenium/standalone-chrome
```
#### 2.3 测试
这里有一点要说明下，这个和前面此处讲的Windows环境的远程chromedriver不太一样的是，这里的远程地址要加上“/wd/hub”路径才行。

```shell
# set opts
# chrome = webdriver.Chrome(options=opts)
chrome = webdriver.Remote("http://192.168.2.88:9515/wd/hub", options=opts)
```






### 3. selenium/standalone-chrome-debug

这是一个单机版本的镜像，这个里面包括一个chrome浏览器，可以用来调试selenium程序。它和selenium/standalone-chrome的区别在于，这个里面还会启动一个VNC服务，可以通过VNC客户端连接进来浏览器的执行情况，便于调试selenium程序。

#### 3.1 拉取镜像
```shell
selenium/standalone-chrome-debug
```
#### 3.2 启动容器
此处除了映射了chromedriver的默认端口4444到宿主机的9515端口外，还将VNC的5900端口也映射到宿主机的5900端口上面了。
```
docker run -tid --name selenium-standalone-chrome-debug -h selenium-standalone-chrome-debug --memory 1g --memory-swap -1 -p 9515:4444 -p 5900:5900 selenium/standalone-chrome-debug
```
#### 3.3 测试服务

```shell
# set opts
# chrome = webdriver.Chrome(options=opts)
chrome = webdriver.Remote("http://192.168.2.88:9515/wd/hub", options=opts)
```
#### 3.4 测试VNC
打开VNC软件，设置VNC主机为192.168.2.88:5900，进入后可以看到chromedriver环境的桌面，启动selenium程序后，可以看到浏览器执行过程。

VNC默认密码是secret！！！

连接到VNC可以看到这样的桌面。

启动selenium程序后，可以看到浏览器正在打开“同福网”页面。

我们可以进入到chromedriver虚拟机里面进行F12调试操作。

### 4. selenium/hub和selenium/node-chrome
这是一个hub和node组成的多节点处理中心，它可以将多个Chrome浏览器集中到一起进行任务处理，可以实现给执行相同任务的Chrome浏览器扩容的目的。

#### 4.1 拉取镜像
selenium/hub
selenium/node-chrome

##### 4.2 启动hub容器
先启动hub镜像，使用`GRID_MAX_SESSION`设置开启的最大会话数量。为了后面的node方便连接此处给这个容器加到了bridge2网络里面了。因为我们的`selenium`访问的是这个hub容器，所以此处将它的4444端口映射到了宿主机的9515端口上面。

```shell
docker run -tid --name selenium-hub -h selenium-hub \
-e GRID_MAX_SESSION=10 \
--memory 128m --memory-swap -1 --net bridge2 -p 9515:4444 selenium/hub
```
启动信息里的XPUB端口和XSUB端口请记住，后面会用到它们！

#### 4.3 启动node容器
接下来启动node镜像，这里的`SE_EVENT_BUS_HOST`就是上面hub的名称，`SE_EVENT_BUS_PUBLISH_PORT`就是hub的`XPUB`端口，`SE_EVENT_BUS_SUBSCRIBE_PORT`就是hub的XSUB端口。同样的此处将这个容器加到了bridge2网络里面了。

```shell
docker run -tid --name selenium-node-chrome -h selenium-node-chrome \
-e SE_EVENT_BUS_HOST=selenium-hub \
-e SE_EVENT_BUS_PUBLISH_PORT=4442 \
-e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
--memory 1g --memory-swap -1 --net bridge2 selenium/node-chrome
```

可以看到，此处节点添加成功了！

#### 4.4 测试
此处和单机版本一样，没有监视的功能，仅是一个运行的单体程序

```shell
# set opts
# chrome = webdriver.Chrome(options=opts)
chrome = webdriver.Remote("http://192.168.2.88:9515/wd/hub", options=opts)
```