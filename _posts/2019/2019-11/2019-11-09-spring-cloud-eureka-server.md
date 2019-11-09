---
layout: post
title:  "SpringCloud之Eureka"
categories: Spring SpringCloud
tags:  spring springcloud
author: Franklinfang
---

* content
{:toc}

# SpringCloud Eureka 入门
## 背景
服务发现是基于微服务的体系结构的关键原则之一。尝试手工配置每个客户端或某种形式的约定可能很困难，而且很脆弱。Eureka是Netflix的服务发现服务器和客户端。可以将服务器配置和部署为高可用性，每个服务器将注册服务的状态复制到其他服务器。

![image](https://user-images.githubusercontent.com/29160332/68530744-34674a80-0346-11ea-81bc-d16972b27db4.png)





## 简介
Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务，主要用于定位运行在AWS域中的中间层服务，以达到负载均衡和中间层服务故障转移的目的。SpringCloud将它集成在其子项目spring-cloud-netflix中，以实现SpringCloud的服务发现功能。

**Eureka包含两个组件：Eureka Server和Eureka Client。**

**Eureka Server**提供服务注册服务，各个节点启动后，会在Eureka Server中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到。

**Eureka Client**是一个java客户端，用于简化与Eureka Server的交互，客户端同时也就是一个内置的、使用轮询(round-robin)负载算法的负载均衡器。

## Eureka特点
- Eureka不持久化，缓存。

- Eureka通过增量更新注册信息，只关心瞬时状态。

- Eureka提供客户端缓存，宁可返回某服务5分钟之前在哪几个服务器上可用的信息，也不能因为暂时的网络故障而找不到可用的服务器。

## Eureka服务注册与发现

![image](https://user-images.githubusercontent.com/29160332/68530795-b9eafa80-0346-11ea-9270-c9aaab8b949b.png)

## 服务注册

- 1将实例注册信息放入或者更新registry

- 2.将实例注册信息加入最近修改的记录队列

- 3.主动让Response缓存失效

## 服务取消

- 1.从registry中剔除这个实例

- 2.将实例注册信息加入最近修改的记录队列

- 3.主动让Response缓存失效

## EurekaClient 缓存

- EurekaClient第一次全量拉取，定时增量拉取应用服务实例信息，保存在缓存中。

- EurekaClient增量拉取失败，或者增量拉取之后对比hashcode发现不一致，就会执行全量拉取，这样避免了网络某时段分片带来的问题。

- 同时对于服务调用，如果涉及到ribbon负载均衡，那么ribbon对于这个实例列表也有自己的缓存，这个缓存定时从EurekaClient的缓存更新

## 自我保护机制

自我保护机制：默认情况下，如果Eureka Server在一定时间内没有接收到某个微服务实例的心跳，Eureka Server将会注销该实例（默认90秒）。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。

Eureka通过“自我保护模式”来解决这个问题——当Eureka Server节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，Eureka Server就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。

综上，自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。

**但是，在我们实际生产中，我们云环境同一个Region下不会发生大规模网络分区状况，所以没有启用自我保护。**

## 父级项目依赖

创建父maven的pom类型项目, ，然后仅仅往pom.xml添加如下依赖即可,可参考如下


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.3.RELEASE</version>
		<relativePath />
	</parent>

	<groupId>com.frankdevhub.service</groupId>
	<artifactId>com-frankdevhub-parent</artifactId>
	<name>com-frankdevhub-parent</name>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<modules>
		<module>cloud-eureka-sever</module>
	</modules>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```
## Eureka Server搭建

**第一步：**创建一个cloud-erueka-server的Model并指定为父级项目
```xml
<?xml version="1.0"?>
<project
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
	xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.frankdevhub.service</groupId>
		<artifactId>com-frankdevhub-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<!-- <relativePath>../pom.xml</relativePath> -->
		<relativePath />
	</parent>

	<artifactId>cloud-eureka-sever</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>cloud-eureka-server</name>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
			<version>1.4.6.RELEASE</version>
		</dependency>
	</dependencies>
</project>

```
**第二步：**配置文件application.yml的配置
``` yml
server:
  port: 8761

spring:
  application:
    name: cloud-eureka-server
    
eureka:
  client:
    registerWithEureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```
**第三步：**在启动类中加入注解@EnableEurekaServer证明是个启动服务注册。
```java
@SpringBootApplication
@EnableEurekaServer
public class CloudEurekaSeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaSeverApplication.class, args);
    }

}
````
## Eureka Client的搭建

**第一步：**创建一个cloud-erueka-client的Model并指定为父级项目
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
		<groupId>com.frankdevhub.service</groupId>
		<artifactId>com-frankdevhub-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<!-- <relativePath>../pom.xml</relativePath> -->
		<relativePath />
	</parent>

	<artifactId>cloud-eureka-sever</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>
    <name>cloud-eureka-client</name>

    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**第二步：**配置application.yml连接到erueka-server端
```yml
server:
  port: 8762

spring:
  application:
    name: cloud-eureka-client

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/

```
**第三步：**启动类@EnableEurekaClient指定为客户端

```java
@SpringBootApplication
@EnableEurekaClient
@RestController
public class CloudEurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClientApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "zero") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
```

最后在浏览器中打开地址:http://localhost:8761/ 就可以看到客户端都注册了。