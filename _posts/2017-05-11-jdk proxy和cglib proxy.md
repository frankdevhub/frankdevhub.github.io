---
layout: post
title: jdk proxy和cglib proxy
date:   2017-05-11 08:50:20
categories: java
---

说明：`spring AOP`就是用aspectj来实现的，是依赖关系！`AspectJ`是`动态代理`的一种实现！而spring默认使用的就是AspectJ来实现的动态代理，spring自己的AOP就是使用AspectJ来实现的！当然你也可以使用其他的实现，如cglib!.

第一种方式：利用JDK的反射机制（proxyJDK）.

导入`aspectjrt.jar`;`aspectjwear.jar`.



[代码详见源码]；(https://github.com/frankdevhub/ToolBox/tree/master/frankdevhub.github.io/20170511-jdk%20proxy%E5%92%8Ccglib%20proxy)

备注一下问题需要注意：

1.在JDK代理中 public Object invoke(Object proxy, Method method, Object[] args)方法中传参proxy为代理对象（代理类）自身的实例，而不是代理目标类的实例，如果写成代理目标类的实例则会导致无限死循环。

2.基于上述的问题中proxy的使用，如果要打印查看proxy的具体属性，使用System.out.printlin直接打印是错误的。原因在于invoke内部不能调用proxy的相关方法，否则会死循环，因为proxy是Proxy.getInstance得到的代理类，执行代理的代理方法都会先出发invoke,因此会导致invoke,toString触发invoke然后再次触发相同方法。


常见错误：

![error](https://cloud.githubusercontent.com/assets/22045217/26286119/c5bf5410-3e8f-11e7-9671-469b882ff074.PNG)



`Cglib动态代理` 
JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理，cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现