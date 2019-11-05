---
layout: post
title:  "kubernetes(k8s)基础介绍"
categories: deploy 容器
tags:  docker kubernetes
author: Franklinfang
---

* content
{:toc}

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

Kubernetes对计算资源进行了更高层次的抽象，通过将容器进行细致的组合，将最终的应用服务交给用户。Kubernetes在模型建立之初就考虑了容器跨机连接的要求，支持多种网络解决方案，同时在Service层次构建集群范围的SDN网络。其目的是将服务发现和负载均衡放置到容器可达的范围，这种透明的方式便利了各个服务间的通信，并为微服务架构的实践提供了平台基础。而在Pod层次上，作为Kubernetes可操作的最小对象，其特征更是对微服务架构的原生支持。

Kubernetes项目来源于Borg，可以说是集结了Borg设计思想的精华，并且吸收了Borg系统中的经验和教训。

Kubernetes作为容器集群管理工具，于2015年7月22日迭代到 v 1.0并正式对外公布，这意味着这个开源容器编排系统可以正式在生产环境使用。与此同时，谷歌联合Linux基金会及其他合作伙伴共同成立了CNCF基金会( Cloud Native Computing Foundation)，并将Kuberentes 作为首个编入CNCF管理体系的开源项目，助力容器技术生态的发展进步。Kubernetes项目凝结了Google过去十年间在生产环境的经验和教训，从Borg的多任务Alloc资源块到Kubernetes的多副本Pod，从Borg的Cell集群管理，到Kubernetes设计理念中的联邦集群，在Docker等高级引擎带动容器技术兴起和大众化的同时，为容器集群管理提供独了到见解和新思路。

## kubernetes特点

可移植: 支持公有云，私有云，混合云，多重云（multi-cloud） 
可扩展: 模块化, 插件化, 可挂载, 可组合 
自动化: 自动部署，自动重启，自动复制，自动伸缩/扩展

Kubernetes一个核心的特点就是自动化，能够自主的管理容器来保证云平台中的容器按照用户的期望状态运行着（比如用户想让apache一直运行，用户不需要关心怎么去做，Kubernetes会自动去监控，然后去重启，新建，总之，让apache一直提供服务），管理员可以加载一个微型服务，让规划器来找到合适的位置，同时，Kubernetes也系统提升工具以及人性化方面，让用户能够方便的部署自己的应用（就像canary deployments）。

现在Kubernetes着重于不间断的服务状态（比如web服务器或者缓存服务器）和原生云平台应用（Nosql）,在不久的将来会支持各种生产云平台中的各种服务，例如，分批，工作流，以及传统数据库。

所有Kubernetes中的资源，比如Pod,都通过一个叫URI的东西来区分，这个URI有一个UID,URI的重要组成部分是：对象的类型（比如pod），对象的名字，对象的命名空间，对于特殊的对象类型，在同一个命名空间内，所有的名字都是不同的，在对象只提供名称，不提供命名空间的情况下，这种情况是假定是默认的命名空间。UID是时间和空间上的唯一。

## kubernetes相关概念

使用Kubernetes，需要对pods、services、replication controller等概念了然于心。

### 基本对象
####  Pod

Pod是最小部署单元，一个Pod由一个或多个容器组成，Pod中容器共享存储和网络，在同一台Docker主机上运行。 
每个Pod都会包含一个 “根容器”，还会包含一个或者多个紧密相连的业务容器。 

![image](https://user-images.githubusercontent.com/29160332/68131940-d955e200-ff58-11e9-9a31-69539f844598.png)

####  Service

Service一个应用服务抽象，定义了Pod逻辑集合和访问这个Pod集合的策略。 
Service代理Pod集合对外表现是为一个访问入口，分配一个集群IP地址，来自这个IP的请求将负载均衡转发后端Pod中的容器。 
Service通过LableSelector选择一组Pod提供服务。

在K8s集群中，客户端需要访问的服务就是Service对象。每个Service会对应一个集群内部有效的虚拟IP，集群内部通过虚拟IP访问一个服务。在K8s集群中微服务的负载均衡是由Kube-proxy实现的。Kube-proxy是K8s集群内部的负载均衡器。它是一个分布式代理服务器，在K8s的每个节点上都有一个；这一设计体现了它的伸缩性优势，需要访问服务的节点越多，提供负载均衡能力的Kube-proxy就越多，高可用节点也随之增多。与之相比，我们平时在服务器端做个反向代理做负载均衡，还要进一步解决反向代理的负载均衡和高可用问题。

“Service微服务”，kubernetes中的核心。通过分析、识别并建模系统中的所有服务为微服务，最终系统有多个提供不同业务能力而又彼此独立的微服务单元所组成，服务之间通过TCP/IP进行通信。每个Pod都会被分配一个单独的IP地址，而且每个Pod都提供了一个独立的Endpoint以被客户端访问。

客户端想要访问到Pod中的服务需要 部署负载均衡器，为Pod开启对外服务端口，将Pod的Endpoint列表加入转发列表中，客户端通过负载均衡器的对外IP+Port来访问此服务。每个Service都有一个全局唯一的虚拟ClusterIP，这样每个服务就变成了具备唯一IP地址的“通信节点”，服务调用就变成了最基础的TCP网络通信问题。

####  Volume

数据卷，是Pod中能够被多个容器访问的共享目录。定义在Pod之上，被一个Pod里的多个容器挂载到具体的文件目录之下；与Pod生命周期相同。

可以让一个Pod里的多个容器共享文件、让容器的数据写到宿主机的磁盘上或者写文件到 共享存储中，具体如下图所示： 

![image](https://user-images.githubusercontent.com/29160332/68132038-fab6ce00-ff58-11e9-967a-0b0d741a2266.png)

### 持久存储卷（Persistent Volume，PV）和持久存储卷声明（Persistent Volume Claim，PVC）

PV和PVC使得K8s集群具备了存储的逻辑抽象能力，使得在配置Pod的逻辑里可以忽略对实际后台存储技术的配置，而把这项配置的工作交给PV的配置者，即集群的管理者。存储的PV和PVC的这种关系，跟计算的Node和Pod的关系是非常类似的；PV和Node是资源的提供者，根据集群的基础设施变化而变化，由K8s集群管理员配置；而PVC和Pod是资源的使用者，根据业务服务的需求变化而变化，有K8s集群的使用者即服务的管理员来配置。

#### Namespace

命名空间将对象逻辑上分配到不同Namespace，可以是不同的项目、用户等区分管理，并设定控制策略，从而实现多租户。 
命名空间也称为虚拟集群。

#### Lable

标签用于区分对象（比如Pod、Service）； 
每个对象可以有多个标签，通过标签关联对象。 
是一个key=value的键值对，其中key与value由用户自己指定。可以附加到各种资源对象上，一个资源对象可以定义任意数量的Label。可以通过LabelSelector（标签选择器）查询和筛选资源对象。 

![image](https://user-images.githubusercontent.com/29160332/68132142-25a12200-ff59-11e9-9ed2-791b9fc4d7a7.png)


### 基于基本对象更高层次抽象
#### ReplicationController

Replication Controller声明某个Pod的副本数在任意时刻都符合某个预期值。定义包含如下： 

 - Pod期待的副本数（replicas） 
 - 用于筛选目标Pod的Label Selector 
 - 当Pod副本数小于期望时，用于新的创建Pod的模板template 
 - 通过改变RC里的Pod副本数量，可以实现Pod的扩容或缩容功能 
 - 通过改变RC里Pod模板中的镜像版本，可以实现Pod的滚动升级功能 

![image](https://user-images.githubusercontent.com/29160332/68132354-8597c880-ff59-11e9-8a3e-bd795ecc7197.png)


#### ReplicaSet

下一代ReplicationController。确保任何给定时间指定的Pod副本数量，并提供声明式更新等功能。 
RC与RS唯一区别就是lableselector支持不同，RS支持新的基于集合的标签，RC仅支持基于等式的标签。

在kubernetes1.2的时候，RC就由Replication Controller升级成Replica Set，“下一代RC”。命令兼容适用，Replica Set主要被Deployment这个更高层的资源对象所使用，从而形成一套Pod创建、删除、更新的编排机制。当我们使用Deployment时，无需关心它是如何创建和维护ReplicaSet的，这一切是自动发生的。

#### Deployment
Deployment是一个更高层次的API对象，它管理ReplicaSets和Pod，并提供声明式更新等功能。 
官方建议使用Deployment管理ReplicaSets，而不是直接使用ReplicaSets，这就意味着可能永远不需要直接操作ReplicaSet对象。

#### StatefulSet
StatefulSet适合持久性的应用程序，有唯一的网络标识符（IP），持久存储，有序的部署、扩展、删除和滚动更新。

#### DaemonSet
DaemonSet确保所有（或一些）节点运行同一个Pod。当节点加入Kubernetes集群中，Pod会被调度到该节点上运行，当节点从集群中移除时，DaemonSet的Pod会被删除。删除DaemonSet会清理它所有创建的Pod。

Job
一次性任务，运行完成后Pod销毁，不再重新启动新容器。还可以任务定时运行。

### 有状态服务集（PetSet）

K8s在1.3版本里发布了Alpha版的PetSet功能。在云原生应用的体系里，有下面两组近义词；第一组是无状态（stateless）、牲畜（cattle）、无名（nameless）、可丢弃（disposable）；第二组是有状态（stateful）、宠物（pet）、有名（having name）、不可丢弃（non-disposable）。RC和RS主要是控制提供无状态服务的，其所控制的Pod的名字是随机设置的，一个Pod出故障了就被丢弃掉，在另一个地方重启一个新的Pod，名字变了、名字和启动在哪儿都不重要，重要的只是Pod总数；而PetSet是用来控制有状态服务，PetSet中的每个Pod的名字都是事先确定的，不能更改。PetSet中Pod的名字的作用，并不是《千与千寻》的人性原因，而是关联与该Pod对应的状态。

对于RC和RS中的Pod，一般不挂载存储或者挂载共享存储，保存的是所有Pod共享的状态，Pod像牲畜一样没有分别（这似乎也确实意味着失去了人性特征）；对于PetSet中的Pod，每个Pod挂载自己独立的存储，如果一个Pod出现故障，从其他节点启动一个同样名字的Pod，要挂载上原来Pod的存储继续以它的状态提供服务。

适合于PetSet的业务包括数据库服务MySQL和PostgreSQL，集群化管理服务Zookeeper、etcd等有状态服务。PetSet的另一种典型应用场景是作为一种比普通容器更稳定可靠的模拟虚拟机的机制。传统的虚拟机正是一种有状态的宠物，运维人员需要不断地维护它，容器刚开始流行时，我们用容器来模拟虚拟机使用，所有状态都保存在容器里，而这已被证明是非常不安全、不可靠的。使用PetSet，Pod仍然可以通过漂移到不同节点提供高可用，而存储也可以通过外挂的存储来提供高可靠性，PetSet做的只是将确定的Pod与确定的存储关联起来保证状态的连续性。PetSet还只在Alpha阶段，后面的设计如何演变，我们还要继续观察。

### 集群联邦（Federation）

K8s在1.3版本里发布了beta版的Federation功能。在云计算环境中，服务的作用距离范围从近到远一般可以有：同主机（Host，Node）、跨主机同可用区（Available Zone）、跨可用区同地区（Region）、跨地区同服务商（Cloud Service Provider）、跨云平台。K8s的设计定位是单一集群在同一个地域内，因为同一个地区的网络性能才能满足K8s的调度和计算存储连接要求。而联合集群服务就是为提供跨Region跨服务商K8s集群服务而设计的。

每个K8s Federation有自己的分布式存储、API Server和Controller Manager。用户可以通过Federation的API Server注册该Federation的成员K8s Cluster。当用户通过Federation的API Server创建、更改API对象时，Federation API Server会在自己所有注册的子K8s Cluster都创建一份对应的API对象。在提供业务请求服务时，K8s Federation会先在自己的各个子Cluster之间做负载均衡，而对于发送到某个具体K8s Cluster的业务请求，会依照这个K8s Cluster独立提供服务时一样的调度模式去做K8s Cluster内部的负载均衡。而Cluster之间的负载均衡是通过域名服务的负载均衡来实现的。

所有的设计都尽量不影响K8s Cluster现有的工作机制，这样对于每个子K8s集群来说，并不需要更外层的有一个K8s Federation，也就是意味着所有现有的K8s代码和机制不需要因为Federation功能有任何变化。

### 管理相关
#### 密钥对象（Secret）

Secret是用来保存和传递密码、密钥、认证凭证这些敏感信息的对象。使用Secret的好处是可以避免把敏感信息明文写在配置文件里。在K8s集群中配置和使用服务不可避免的要用到各种敏感信息实现登录、认证等功能，例如访问AWS存储的用户名密码。为了避免将类似的敏感信息明文写在所有需要使用的配置文件中，可以将这些信息存入一个Secret对象，而在配置文件中通过Secret对象引用这些敏感信息。这种方式的好处包括：意图明确，避免重复，减少暴漏机会。

#### 用户帐户（User Account）和服务帐户（Service Account）

顾名思义，用户帐户为人提供账户标识，而服务账户为计算机进程和K8s集群中运行的Pod提供账户标识。用户帐户和服务帐户的一个区别是作用范围；用户帐户对应的是人的身份，人的身份与服务的namespace无关，所以用户账户是跨namespace的；而服务帐户对应的是一个运行中程序的身份，与特定namespace是相关的。

#### RBAC访问授权

K8s在1.3版本中发布了alpha版的基于角色的访问控制（Role-based Access Control，RBAC）的授权模式。相对于基于属性的访问控制（Attribute-based Access Control，ABAC），RBAC主要是引入了角色（Role）和角色绑定（RoleBinding）的抽象概念。在ABAC中，K8s集群中的访问策略只能跟用户直接关联；而在RBAC中，访问策略可以跟某个角色关联，具体的用户在跟一个或多个角色相关联。显然，RBAC像其他新功能一样，每次引入新功能，都会引入新的API对象，从而引入新的概念抽象，而这一新的概念抽象一定会使集群服务管理和使用更容易扩展和重用。

### Kubernetes架构

![image](https://user-images.githubusercontent.com/29160332/68132668-0951b500-ff5a-11e9-8894-1fc3a8f529e5.png)

![image](https://user-images.githubusercontent.com/29160332/68132688-12428680-ff5a-11e9-82ec-f0cbd9d069be.png)

### Master组件

Master：集群控制管理节点，所有的命令都经由master处理。

![image](https://user-images.githubusercontent.com/29160332/68132720-21c1cf80-ff5a-11e9-9287-e79ace8ea78c.png)

#### kube-apiserver

Kubernetes API，集群的统一入口，各组件协调者，以HTTPAPI提供接口服务，所有对象资源的增删改查和监听操作都交给APIServer处理后再提交给Etcd存储。

#### kube-controller-manager

处理集群中常规后台任务，一个资源对应一个控制器，而ControllerManager就是负责管理这些控制器的。

#### kube-scheduler

根据调度算法为新创建的Pod选择一个Node节点。

### Node组件

Node：是kubernetes集群的工作负载节点。Master为其分配工作，当某个Node宕机时，Master会将其工作负载自动转移到其他节点。

![image](https://user-images.githubusercontent.com/29160332/68132824-4f0e7d80-ff5a-11e9-89c7-d2cda1087c26.png)

#### kubelet

kubelet是Master在Node节点上的Agent，管理本机运行容器的生命周期，比如创建容器、Pod挂载数据卷、下载secret、获取容器和节点状态等工作。kubelet将每个Pod转换成一组容器。

#### kube-proxy

在Node节点上实现Pod网络代理，维护网络规则和四层负载均衡工作。

#### docker或rocket(rkt)

运行的容器。

### 第三方服务
#### etcd简介

分布式键值存储系统。用于保持集群状态，比如Pod、Service等对象信息。 
etcd是受Zookeeper与doozer启发而催生的项目。

![image](https://user-images.githubusercontent.com/29160332/68132981-9268ec00-ff5a-11e9-91fb-1e0f83cd21c2.png)

#### etcd存储

etcd的存储分为内部存储和持久化（硬盘）存储两部分。内存中的存储除了顺序化地记录所有用户对节点数据变更的记录外，还会对用户数据进行索引、建堆等方便查询的操作。而持久化则使用WAL进行记录存储。在k8s中，所有数据的存储以及操作记录都在etcd中进行存储，所以对于k8s集群来说，etcd是相当重要的，一旦故障，可能导致整个集群的瘫痪或者数据丢失。

在WAL体系中，所有的数据在提交之前都会进行日志记录。持久化存储的目录分为两个：snap和wal。snapshot相当于数据压缩，默认会将10000条wal操作记录merge成snapshot，节省存储，又保证数据不会丢失。

WAL：存储所有事务的变化记录 
Snapshot：用于存放某一时刻etcd所有目录的数据

#### etcd核心算法

etcd的核心算法是raft算法，强一致性算法。具体如下图所示 

![image](https://user-images.githubusercontent.com/29160332/68133071-b6c4c880-ff5a-11e9-9f83-79d344f5b655.png)

![image](https://user-images.githubusercontent.com/29160332/68133091-bdebd680-ff5a-11e9-812b-da5aaa2e56c2.png)

注意：由于etcd是负责存储，所以不建议搭建单点集群，如zookeeper一样，由于存在选举策略，所以一般推荐奇数个集群，如3，5，7。只要集群半数以上的结点存活，那么集群就可以正常运行，否则集群可能无法正常使用。

### Kubernetes网络相关

Kubernetes为每个Pod都分配了唯一的IP地址，称之为PodIP，一个Pod里的多个容器共享PodIP地址。要求底层网络支持集群内任意两个Pod之间的直接通信，通常采用虚拟二层网络技术来实现（Flannel）。

Kubernetes支持一种特殊的网络模型，Kubernetes创建了一个地址空间，并且不动态的分配端口，它可以允许用户选择任何想使用的端口，为了实现这个功能，它为每个Pod分配IP地址。

现代互联网应用一般都会包含多层服务构成，比如web前台空间与用来存储键值对的内存服务器以及对应的存储服务，为了更好的服务于这样的架构，Kubernetes提供了服务的抽象，并提供了固定的IP地址和DNS名称，而这些与一系列Pod进行动态关联，这些都通过之前提到的标签进行关联，所以我们可以关联任何我们想关联的Pod，当一个Pod中的容器访问这个地址的时候，这个请求会被转发到本地代理（kube proxy）,每台机器上均有一个本地代理，然后被转发到相应的后端容器。Kubernetes通过一种轮训机制选择相应的后端容器，这些动态的Pod被替换的时候,Kube proxy时刻追踪着，所以，服务的IP地址（dns名称），从来不变。

### Kubernetes主要功能

#### 数据卷

Pod中容器之间共享数据，可以使用数据卷。

#### 应用程序健康检查
容器内服务可能进程堵塞无法处理请求，可以设置监控检查策略保证应用健壮性。

#### 复制应用程序实例

控制器维护着Pod副本数量，保证一个Pod或一组同类的Pod数量始终可用。

#### 弹性伸缩

根据设定的指标（CPU利用率）自动缩放Pod副本数。

#### 服务发现

使用环境变量或DNS服务插件保证容器中程序发现Pod入口访问地址。

####负载均衡

一组Pod副本分配一个私有的集群IP地址，负载均衡转发请求到后端容器。在集群内部其他Pod可通过这个ClusterIP访问应用。

####滚动更新

更新服务不中断，一次更新一个Pod，而不是同时删除整个服务。

#### 服务编排

通过文件描述部署服务，使得应用程序部署变得更高效。

####资源监控

Node节点组件集成cAdvisor资源收集工具，可通过Heapster汇总整个集群节点资源数据，然后存储到InfluxDB时序数据库，再由Grafana展示。

#### 提供认证和授权

支持属性访问控制（ABAC）、角色访问控制（RBAC）认证授权策略。

### Kubernetes命令行管理工具

使用kubectl来管理Kubernetes集群。

可以在 

[https://github.com/kubernetes/kubernetes](https://github.com/kubernetes/kubernetes)
 
[https://www.kubernetes.org.cn/doc-45](https://www.kubernetes.org.cn/doc-45) 

找到更多的信息。

### Kubernetes UI/dashboard

Kubernetes有一个基于web的用户界面，它可以图表化显示当前集群状态。

Kubernetes界面默认是作为集群插件部署的。要访问它需要进入 https:///ui 这个地址，之后会重定向到 https:///api/v1/proxy/namespaces/kube-system/services/kube-ui/#/dashboard/ 。

如果你发现你无法访问UI，那有可能是Kubernetes UI服务在你的集群上还没有启动。如果是这样，你可以手动开启UI服务：

```
kubectl create -f cluster/addons/kube-ui/kube-ui-rc.yaml –namespace=kube-system
kubectl create -f cluster/addons/kube-ui/kube-ui-svc.yaml –namespace=kube-system
```

通常，这些应该通过 kube-addons.sh 脚本自动地被运行在主节点上。

### k8s相关经验

#### Pod的管理

在Kubernetes中，所有的容器均在Pod中运行,一个Pod可以承载一个或者多个相关的容器，在后边的案例中，同一个Pod中的容器会部署在同一个物理机器上并且能够共享资源。一个Pod也可以包含O个或者多个磁盘卷组（volumes）,这些卷组将会以目录的形式提供给一个容器，或者被所有Pod中的容器共享，对于用户创建的每个Pod,系统会自动选择那个健康并且有足够容量的机器，然后创建类似容器的容器,当容器创建失败的时候，容器会被node agent自动的重启,这个node agent叫kubelet,但是，如果是Pod失败或者机器，它不会自动的转移并且启动，除非用户定义了 replication controller。

用户可以自己创建并管理Pod,Kubernetes将这些操作简化为两个操作：基于相同的Pod配置文件部署多个Pod复制品；创建可替代的Pod当一个Pod挂了或者机器挂了的时候。而Kubernetes API中负责来重新启动，迁移等行为的部分叫做“replication controller”，它根据一个模板生成了一个Pod,然后系统就根据用户的需求创建了许多冗余，这些冗余的Pod组成了一个整个应用，或者服务，或者服务中的一层。一旦一个Pod被创建，系统就会不停的监控Pod的健康情况以及Pod所在主机的健康情况，如果这个Pod因为软件原因挂掉了或者所在的机器挂掉了，replication controller 会自动在一个健康的机器上创建一个一摸一样的Pod,来维持原来的Pod冗余状态不变，一个应用的多个Pod可以共享一个机器。

我们经常需要选中一组Pod，例如，我们要限制一组Pod的某些操作，或者查询某组Pod的状态，作为Kubernetes的基本机制，用户可以给Kubernetes Api中的任何对象贴上一组 key:value 的标签，然后，我们就可以通过标签来选择一组相关的Kubernetes Api 对象，然后去执行一些特定的操作，每个资源额外拥有一组（很多） keys 和 values,然后外部的工具可以使用这些keys和vlues值进行对象的检索，这些Map叫做annotations（注释）。

#### 网络配置

如何从Internet访问部署的应用？

部署前的服务有一个IP地址，但这个地址仅在Kubernetes集群中可用。这意味着无法通过网络访问该服务！在Google Cloud Engine上运行时，Kubernetes会自动配置一个负载均衡用以访问应用；如果不在Google Cloud Engine上运行（比如我们），那就需要做一些额外的工作来获得负载均衡了。

直接在主机端口上开放服务是一个可行的解决方案（很多人一开始的确是这么做的），但我们发现，这样的做法等于放弃了Kubernetes所提供的许多好处。如果我们依赖主机上的端口，部署多个应用时会遇到端口冲突。另外这样的做法会加大扩展集群和更换主机的难度。

#### 二级负载均衡器配置

我们发现，解决以上问题的更好办法，是在Kubernetes集群前配置负载均衡器，例如HAProxy或者NGINX。于是我们开始在AWS上的VPN中运行Kubernetes集群，并使用AWS ELB将外部web流量路由到内部HAProxy集群。HAProxy为每个Kubernetes服务配置了“后端”，以便将流量交换到各个pods。

在任何情况下，创建新的Kubernetes服务，我们都需要一种机制动态重新配置负载均衡器（在我们的例子中是HAProxy）。

Kubernetes社区目前正在开发一个名为ingress的功能，用来直接从Kubernetes配置外部负载均衡器。可惜的是，目前开发工作还未完成。过去一年，我们采用的是API配合一个小的开源工具来配置负载均衡。

#### 配置负载均衡

首先，我们需要一个地方存储负载均衡器配置。负载均衡器配置可以存储在任何地方，不过因为我们已经有etcd可用，就把这些配置放在了etcd里。我们使用一个名为“confd”的工具观察etcd中的配置变动，并用模板生成了一个新的HAProxy配置文件。当一个新的服务添加到Kubernetes，我们向etcd中添加一个新的配置，一个新的HAProxy配置文件也就此产生。

#### 资源限制
使用Kubernetes时，搞清楚资源限制很重要。你可以在每个pod上配置资源请求和CPU／内存限制，也可以控制资源保证和bursting limits。

这些设置对于高效运行多个容器极为重要，防止容器因分配内存不足而意外停止。

建议尽早设置和测试资源限制。没有限制时，看起来运行良好，不代表把重要负载放到容器中不会出现问题。

#### Kubernetes监控
当我们快要搭建好Kubernetes时，我们意识到监控和日志在这个新的动态环境中非常重要。当我们面对大规模服务和节点时，进入服务器查看日志文件是不现实的，建一个中心化的日志和监控系统需要尽快提上议程。

#### 日志
有很多用于日志记录的开源工具，我们使用的是Graylog和Apache Kafka（从容器收集摘录日志的消息传递系统）。容器将日志发送给Kafka，Kafka交给Graylog进行索引。我们让应用组件将日志打给Kafka，方便将日志流式化，成为易于索引的格式。也有一些工具可以从容器外收集日志，然后发送给日志系统。

#### 监控
Kubernetes具备超强的故障恢复机制，Kubernetes会重启意外停止的pod。我们曾遇到过因内存泄露，导致容器在一天内宕机多次的情况，然而令人惊讶的是，甚至我们自己都没有察觉到。

Kubernetes在这一点上做得非常好，不过一旦问题出现，即使被及时处理了，我们还是想要知道。因此我们使用了一个自定义的运行状况检查仪表盘来监控Kubernetes节点和pod，以及包括数据存储在内的一些服务。可以说在监控方面，Kubernetes API再次证明了其价值所在。

检测负载、吞吐量、应用错误等状态也是同样重要的，有很多开源软件可以满足这一需求。我们的应用组件将指标发布到InfluxDB，并用Heapster去收集Kubernetes的指标。我们还通过Grafana（一款开源仪表盘工具）使存储在InfluxDB中的指标可视化。有很多InfluxDB／Grafana堆栈的替代方案，无论你才用哪一种，对于运行情况的跟踪和监控都是非常有价值的。

#### 数据存储和Kubernetes
很多Kubernetes新用户都有一个问题：我该如何使用Kubernetes处理数据？

运行数据存储时（如MangoDB或MySQL），我们很可能会有持久化数据储存的需求。不过容器一但重启，所有数据都会丢失，这对于无状态组件没什么影响，但对持久化数据储存显然行不通。好在，Kubernetes具有Volume机制。

Volume可以通过多种方式备份，包括主机文件系统、EBS（AWS的Elastic Block Store）和nfs等。当我们研究持久数据问题是，这是一个很好的方案，但不是我们运行数据存储的答案。

#### 副本问题
在大多数部署中，数据存储也是有副本的。Mongo通常在副本集中运行，而MySQL可以在主／副模式下运行。我们都知道数据储存集群中的每个节点应该备份在不同的volume中，写入相同volume会导致数据损坏。另外，大多数数据存储需要明确配置才能使集群启动并运行，自动发现和配置节点并不常见。

同时，运行着数据存储的机器通常会针对某项工作负载进行调整，例如更高的IOPS。扩展（添加/删除节点）对于数据存储来说，也是一个昂贵的操作，这些都和Kubernetes部署的动态本质不相符。

决定不在生产环境数据存储上使用Kubernetes

以我们的情况，在Kubernetes内运行数据存储没有想象中那么完美，设置起来也比其他Kubernetes部署复杂得多。

于是我们决定不在生产环境数据存储上使用Kubernetes，而是选择在不同的机器上手动启动这些集群，我们在Kubernetes内部运行的应用正常连接到数据存储集群。

所以说，没必要运行Kubernetes的一切，按自身情况与其他工具配合使用，会有意想不到的效果，比如我们的数据存储和HAProxy服务器。

## 未来
看看我们现在的部署，可以负责任的说，Kubernetes的表现绝对是梦幻级的，而Kubernetes API更是自动化部署流程的利器。由于不需要处理VM，我们现在的部署相比之前更快、更可靠。更简单的容器测试和交付，也让我们在构建和部署可靠性上得到了巨大提升。

这种新的部署方式迅速高效，让我们得以跟上其他团队的节奏，这绝对是必要的。

## 成本计算
任何事情都有两面性。运行Kubernetes，需要一个etcd集群以及一个Master节点，对于较小的部署来说，这一开销还是比较大的，适合通过一些云服务达成。

对于大规模部署，Kubernetes可以帮助节省大量服务器成本，etcd集群和Master节点这点开销就显得微不足道了。Kubernetes让很多容器在一个主机上运行变得非常容易，最大程度上利用了现有资源，减少了服务器数量，成本自然下降了。不过这样的集群也给运维工作提出了更高的要求，必须要的时候，我们可以选择一些云计算平台提供的云服务来轻松达成。

## 进阶路线图

如果你对Kubernetes不是很熟悉的话, 我们建议你按顺序阅读下面的部分:

- 1.快速入门: 运行并展示一个应用程序

- 2.容器的配置与启动: 配置容器的公共参数 

- 3.部署可持续运行的容器 

- 4.网络配置和链接应用程序: 将应用程序展示给客户和用户 

- 5.在生产环境中是用容器 

- 6.部署管理 

- 7.应用程序的自我检查和调试 
 
- 8.使用Kubernetes的Web用户界面 

- 9.日志 

- 10.监控 

- 11.通过exec命令进入容器 

- 12.通过代理连接容器 

- 13.通过接口转发连接容器

