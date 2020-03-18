---
layout: post
title:  "Concurrency Design Pattern"
date:   2020-02-09 03:29:13
categories: Documents
tags: Documents
author: Franklinfang
mathjax: true
---
* content
{:toc}

## Single Threaded Execution模式——能通过这座桥的只有一个人
 - 不使用Single Threaded Execution模式的程序
   - [UnSafeGate](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Concurrency%20Design%20Pattern/com-frankdevhub-concurrency-pattern/src/com/frankdevhub/pattern/chp1/UnSafeGate.java)
   - [UnSafeMain](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Concurrency%20Design%20Pattern/com-frankdevhub-concurrency-pattern/src/com/frankdevhub/pattern/chp1/UnSafeMain.java)
   - [UnSafeUserThread](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Concurrency%20Design%20Pattern/com-frankdevhub-concurrency-pattern/src/com/frankdevhub/pattern/chp1/UnSafeUserThread.java)
 - 使用Single Threaded Execution模式的程序
   - [SafeGate](https://github.com/frankdevhub/Coding-Laboratory/blob/master/Concurrency%20Design%20Pattern/com-frankdevhub-concurrency-pattern/src/com/frankdevhub/pattern/chp1/SafeGate.java)
 - java.util.comcurrent包和计数信号量
   - []()
   - []()   