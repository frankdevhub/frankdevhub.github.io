---
layout: post
title:  "C++实现基于TCP的Socket通信"
categories: C++ TCP/IP Socket IO
tags:  c++ TCP/IP socket io
author: Franklinfang
---

* content
{:toc}

# c++ 网络编程 TCP/IP

![image](https://user-images.githubusercontent.com/29160332/69917772-b1e52d00-14a4-11ea-82dc-574fa2509565.png)

最近闲暇时间多了，有了时间就下定决心学习学习 cpp，之前有一定 cpp 基础。所以 TCP/IP 为背景进行 cpp 的学习。先写一个简单 TCP 的服务端和客户端来体验一下。什么 TCP 呀 UDP 在开始接触网络编程时候是那么陌生，是那么遥远。
其实大家不要脱离实际，自己把他 magic 化，其实一切都是合乎常理和实际的。先从 TCP 来学起。
说到网络编程我们先理解一个术语**套接字**，突破这个术语，我们就向理解 TCP 迈出了一大步。简单比喻一下吧，我们彼此间通过打电话或写信的进行哪种不是面对面的交流，套接字就是我们进行这些交流工具，电话机或是邮箱，仅此而已。





![image](https://user-images.githubusercontent.com/29160332/69917784-d214ec00-14a4-11ea-8944-6c210ec3fb50.png)

TCP 整个流程和打电话差不多，要打电话先买电话，然后安装，申请号，等待呼叫。

- 先买电话机，socket 这个函数就是安装买来电话

``` c++
serv_sock = socket(PF_INET, SOCK_STREAM, 0);
```

- 有了电话后我们去电话局申请电话号码

``` c++
bind(serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr))
```

- 申请完电话号码，就会有师傅上门连线，看看电话通不通

```c++
listen(serv_sock, 5)
```

- 接下里就是坐在家里等电话了

```c++
accept(serv_sock, (struct sockaddr *)&clnt_addr, &clnt_addr_size)
```

## 服务端实现

```c++
#include<stdio.h>
#include<windows.h>
#include<winsock2.h>
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#pragma comment(lib,"WS2_32.lib") //显式连接套接字

int main()
{
	WSADATA data; //定义WSADATA 结构体对象
	WORD w = MAKEWORD(2, 0); //定义版本号
	char scztext[] = "server:welcome!\r\n";  //定义初始化发送到客户端的字符数组
	::WSAStartup(w, &data); //初始化套接字库
	SOCKET s, s1;//定义连接套接字和数据收发套接字句柄
	s = ::socket(AF_INET, SOCK_STREAM, 0);//创建TCP套接字
	sockaddr_in addr, addr2; //定义套接字地址结构
	int n = sizeof(addr2); //获取套接字地址结构大小
	addr.sin_family = AF_INET; //初始化套接字地址结构
	addr.sin_port = htons(8972);
	addr.sin_addr.S_un.S_addr = htonl(INADDR_ANY);
	addr.sin_addr.s_addr = inet_addr("127.0.0.1");  ///服务器ip
	::bind(s, (sockaddr*)&addr, sizeof(addr)); //绑定套接字
	::listen(s, 5); //监听套接字
	printf_s("服务器已启动\r\n");

	while (true)
	{
		s1 = ::accept(s, (sockaddr*)&addr2, &n); //接受连接请求
		if (s1 = NULL)
		{
			printf_s("%客户端已连接上\r\n", inet_ntoa(addr2.sin_addr));
			::send(s1, scztext, sizeof(scztext), 0); //向客户端发送字符数组
		}
		::closesocket(s);
		::closesocket(s1);
		::WSACleanup(); //释放套接字库
		if (getchar()) //如果有输入，则关闭程序
		{
			return 0;
		}
		else
		{
			::Sleep(100);
		}
	}
}
```

## 客户端实现

```c++
#include<iostream>
#include<stdio.h>
#include<windows.h>
#include<winsock2.h>

#define _WINSOCK_DEPRECATED_NO_WARNINGS
#pragma comment(lib,"WS2_32.lib") //显示连接套子库

int main()
{

	WSADATA data; //定义WSDATA结构体对象
	WORD w = MAKEWORD(2, 0); //定义版本号
	::WSAStartup(w, &data); //初始化套接字库
	SOCKET s; //定义连接套接字句柄
	char sztext[10] = { 0 };

	s = ::socket(AF_INET, SOCK_STREAM, 0); //初始化套接字
	sockaddr_in addr;  //定义套接字地址结构
	addr.sin_family = AF_INET; //初始化套接字地址结构
	addr.sin_port = htons(8972);
	addr.sin_addr.S_un.S_addr = inet_addr("127.0.0.1");

	printf_s("客户端已启动\r\n");

	::connect(s, (sockaddr*)&addr, sizeof(addr));
	::recv(s, sztext, sizeof(sztext), 0);
	printf_s("%\s\r\n", sztext);

	::closesocket(s); //关闭套接字句柄
	::WSACleanup(); //释放套接字句柄
	if (getchar()) //如果有输入，则关闭程序
	{
		return 0;
	}
	else
	{
		::Sleep(100);
	}
}
```