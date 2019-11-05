---
layout: post
title:  "使群"
categories: deploy 容器
tags:  basic java
author: Franklinfang
---

* content
{:toc}

# Nginx + Tomcat实现Tomcat集群
## ss

# kubernetes简介
Kubernetes（简称k8s）是Google在2014年6月开源的一个容器集群管理系统，使用Go语言开发，用于管理云平台中多个主机上的容器化的应用，Kubernetes的目标是让部署容器化的应用简单并且高效,Kubernetes提供了资源调度、部署管理、服务发现、扩容缩容、监控，维护等一整套功能。，努力成为跨主机集群的自动部署、扩展以及运行应用程序容器的平台。 它支持一系列容器工具, 包括Docker等。

![image](https://user-images.githubusercontent.com/29160332/68131393-f4742200-ff57-11e9-86dd-d4a6577194cf.png)





相关链接:

[官方网站](https://kubernetes.io/)

[Kubernetes中文社区文档](http://docs.kubernetes.org.cn/)

[Kubernetes中文社区](https://www.kubernetes.org.cn/k8s)


## kubernetes背景和历史

大规模容器集群管理工具，从Borg到Kubernetes

在Docker 作为高级容器引擎快速发展的同时，Google也开始将自身在容器技术及集群方面的积累贡献出来。在Google内部，容器技术已经应用了很多年，Borg系统运行管理着成千上万的容器应用，在它的支持下，无论是谷歌搜索、Gmail还是谷歌地图，可以轻而易举地从庞大的数据中心中获取技术资源来支撑服务运行。

Borg是集群的管理器，在它的系统中，运行着众多集群，而每个集群可由成千上万的服务器联接组成，Borg每时每刻都在处理来自众多应用程序所提交的成百上千的Job, 对这些Job进行接收、调度、启动、停止、重启和监控。正如Borg论文中所说，Borg提供了3大好处:

- 隐藏资源管理和错误处理，用户仅需要关注应用的开发。

- 服务高可用、高可靠。

- 可将负载运行在由成千上万的机器联合而成的集群中。

作为Google的竞争技术优势，Borg理所当然的被视为商业秘密隐藏起来，但当Tiwtter的工程师精心打造出属于自己的Borg系统（Mesos）时， Google也审时度势地推出了来源于自身技术理论的新的开源工具。

2014年6月，谷歌云计算专家埃里克·布鲁尔（Eric Brewer）在旧金山的发布会为这款新的开源工具揭牌，它的名字Kubernetes在希腊语中意思是船长或领航员，这也恰好与它在容器集群管理中的作用吻合，即作为装载了集装箱（Container）的众多货船的指挥者，负担着全局调度和运行监控的职责。

虽然Google推出Kubernetes的目的之一是推广其周边的计算引擎（Google Compute Engine）和谷歌应用引擎（Google App Engine）。但Kubernetes的出现能让更多的互联网企业可以享受到连接众多计算机成为集群资源池的好处。

Kubernetes对计算资源进行了更高层次的抽象，通过将容器进行细致的组合，将最终的应