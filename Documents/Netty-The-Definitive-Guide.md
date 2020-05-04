---
layout: post
title:  "Netty:The Definitive Guide"
date:   2020-02-09 03:29:13
categories: Documents
tags: Documents
author: Franklinfang
mathjax: true
---
* content
{:toc}

## NIO入门
 - 同步阻塞式I/O创建的TimeServer源码分析
   - [同步阻塞I/O的TimeServer](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/TimeServer.java)
   - [同步阻塞I/O的TimeServerHandler](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/TimeServerHandler.java)
 - 同步阻塞式I/O创建的TimeClient源码分析 
   - [同步阻塞I/O的TimeClient](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/TimeServerHandler.java)
 - 伪异步I/O创建的TimeServer源码分析 
   - [伪异步I/O的TimeServer](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/ExtendTimeServer.java)
   - [伪异步I/O的TimeServerHandlerExecutePool](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/TimeServerHandlerExecutePool.java)
 - NIO创建的TimeServer源码分析
   - [NIO时间服务器TimeServer](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/NIOTimeServer.java)
   - [NIO时间服务器MultiplexerTimeServer](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Netty_The%20Definitive%20Guide_2nd%20Edition/NIO%E5%85%A5%E9%97%A8/basic-nio-starter/src/basic/nio/examples/NIOMultiplexerTimeServer.java)
 - NIO创建的TimeClient源码分析
   - [NIO时间服务器客户端TimeClient]()
   - [NIO时间服务器客户端TimeClientHandle]()