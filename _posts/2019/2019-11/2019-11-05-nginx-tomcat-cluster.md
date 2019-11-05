---
layout: post
title:  "使用Nginx单机部署多Tomcat应用实现Tomcat集群"
categories: deploy 容器
tags:  tomcat java
author: Franklinfang
---

* content
{:toc}


# Nginx + Tomcat实现Tomcat集群

![image](https://user-images.githubusercontent.com/29160332/68220483-c90a3980-0022-11ea-8b4c-fae2ecd6536a.png)





- 1.解压两个tomcat文件夹

![image](https://user-images.githubusercontent.com/29160332/68215417-2ea5f800-001a-11ea-8cbc-25ed5935a194.png)

- 2.配置环境变量，sudo vim /etc/profile，空白处加上

![image](https://user-images.githubusercontent.com/29160332/68215520-5b5a0f80-001a-11ea-9d0c-f1f77cdce04b.png)

 修改结束后执行  source  /etc/profile  使环境变量生效 

- 3.编辑tomcat2/bin/catalina.sh，将CATALINA_BASE和CATALINA_HOME替换成环境变量中tomcat2的对应参数

![image](https://user-images.githubusercontent.com/29160332/68215589-775db100-001a-11ea-9f97-d6f164cf1fa3.png)

- 4.修改tomcat2/conf/server.xml，将其中8005，8080，8009端口号+1000，修改成9005，9080，9009。当使用VIM编辑文本时，可以通过“/${关键字快速定位}”，如‘/8080’。

![image](https://user-images.githubusercontent.com/29160332/68215648-8f353500-001a-11ea-948a-d5c13097b724.png)

![image](https://user-images.githubusercontent.com/29160332/68215695-a411c880-001a-11ea-9356-f1cc89d12087.png)

![image](https://user-images.githubusercontent.com/29160332/68215732-b1c74e00-001a-11ea-80be-d2b3d4110077.png)

tomcat这3个端口的作用分别是：

8005端口是用来关闭TOMCAT服务的端口。

连接器监听8009端口，负责和其他的HTTP服务器建立连接。在把Tomcat与其他HTTP服务器集成时，就需要用到这个连接器。

连接器监听8080端口，负责建立HTTP连接。在通过浏览器访问Tomcat服务器的Web应用时，使用的就是这个连接器

- 5.如果是在虚拟机或服务器上，之前限制过端口号，则需要将新的9080端口添加到防火墙。sudo vim /etc/sysconfig/iptables

![image](https://user-images.githubusercontent.com/29160332/68215808-d02d4980-001a-11ea-9cf0-ba207e893419.png)

之后重启防火墙，sudo /etc/init.d/iptables restart。

![image](https://user-images.githubusercontent.com/29160332/68215849-e6d3a080-001a-11ea-8111-45c759b7fe9b.png)

- 6.分别启动tomcat/bin的./startup.sh。访问127.0.0.1:8080,127.0.0.1:9080就都能访问了。替换Tomcat2/webapps/ROOT/tomcat.png的图片，以区分两个端口访问的是不同的应用

![image](https://user-images.githubusercontent.com/29160332/68215870-f4892600-001a-11ea-99af-2fee48109d78.png)

- 7.修改浏览器所在地的host，将本地127.0.0.1赋值给一个域名。vim  /etc/hosts,添加如下域名。之后通过访问  www.mier.com:8080 与www.mier.com:8090就能访问虚拟机上着两个应用了。

<div align=center> ![image](https://user-images.githubusercontent.com/29160332/68215961-14b8e500-001b-11ea-87ee-f5b2bc81710a.png)

- 8.修改nginx/config目录下的nginx.conf文件，引入扩展域名解析文件，在http{}内加入如下命令。

<div align=center> ![image](https://user-images.githubusercontent.com/29160332/68216021-2ac6a580-001b-11ea-99dc-d03d06637aa8.png)

- 9.然后在nginx/config目录下创建vhost文件夹，在其中新增一个6步骤中域名开头的conf文件，如www.mier.com.conf。

![image](https://user-images.githubusercontent.com/29160332/68216059-3ca84880-001b-11ea-9783-fdc8811b0484.png)

保存退出后，重启nginx，sudo nginx/sbin/nginx -s reload。此时访问www.mier.com，会以设置的权重比例访问到对应的tomcat服务。
