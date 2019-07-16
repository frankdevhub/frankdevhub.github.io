---
layout: post
title:  "Concurrent-Programming API Note 常用基础手册"
categories: Java  Concurrent-Programming
tags:  api-note concurrent-programming multi-thread
author: Franklinfang
---

* content
{:toc}

## Concurrent-Programming Basic API 
多线程基础API速查手册，参考文献 `高洪岩` 著`《多线程编程核心技术——Java Multi-thread Programming》`


### Part1 多线程基础
- 1.1 进程和多线程的概念及线程的优点
- 1.2 使用多线程
   - 1.2.1 继承`Thread`类
   - 1.2.2 实现`Runnable`接口
   - 1.2.3 实例变量与线程安全
   - 1.2.4 留意`i--`与`System.out.println()`的异常
- 1.3 `currentThread()`方法
- 1.4 `isAlive()`方法
- 1.5 `sleep()`方法
- 1.6 `getId()`方法
- 1.7 停止线程
   - 1.7.1 停止不了的线程
   - 1.7.2 判断线程是否是停止状态
   - 1.7.3 能停止的线程——异常法
   - 1.7.4 在沉睡中停止
   - 1.7.5 能停止的线程——暴力停止
   - 1.7.6 方法`stop`与`java.lang.ThreadDeath`异常
   - 1.7.7 释放锁的不良后果
   - 1.7.8 使用`return`停止线程
- 1.8 暂停线程
   - 1.8.1 `suspend`与`resume`方法的使用
   - 1.8.2 `suspend`与`resume`方法的缺点——独占
   - 1.8.3 `suspend`与`resume`方法的缺点——不同步
- 1.9 `yield`方法
- 1.10 线程的优先级
   - 1.10.1 线程优先级的继承特性
   - 1.10.2 优先级具有规则性
   - 1.10.3 优先级具有随机性
   - 1.10.4 看谁运行的快
- 1.11 守护进程