---
layout: post
title:  "Java自定义注解与简单应用：实现一个简易版的系统方法级别权限控制"
categories: Java 
tags:  java
author: Franklinfang
---

* content
{:toc}

# 概述

本文主要介绍一下Java自定义注解，并且写个自定义注解应用的案例
请不要过多关注文中使用这种方式实现系统方法级别权限控制的完美性（后台按钮并没有直接根据用户权限决定显示不显示，而是默认都展示，用户操作时才进行此操作的权限认证），这只是一个用来演示自定义注解使用的案例
当然，你也可以直接集成spring-security或shiro，而且会更好

## 自定义注解

从JDK 1.5开始, Java增加了对元数据(MetaData)的支持，也就是 Annotation(注解)
注解可以标记在包、类、属性、方法，方法参数以及局部变量上，且同一个地方可以同时标记多个注解
注解在Java中的应用相当广泛，例如@override @supperwarnning等，而框架中应用的就更多了，框架一般都会有自己的注解体系，那么注解有什么用呢？

- **生成文档** 例如常见的@see，@param，@return，@author等等
- **跟踪代码依赖性，实现替代配置文件功能**
比较常见的是spring 2.5 开始的基于注解配置，作用就是减少xml配置
- **在编译时进行格式检查**
比如我们熟知的@override， 放在方法上，如果你这个方法并不是覆盖了超类方法，则编译时就能检查出
接下来我们实现一个最简单的自定义注解：






```java
  package com.tp.frankdevhub.annotation;

  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;

  /**
   * FileName: AuthorizedLimit
   * Author:   frankdevhub@gmail.com
   * Date:     2019-11-02 03:00
   * Description:权限拦截注解，只要加入这个注解方法的都会校验是否有权限
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface AuthorizedLimit {

  }
```

上面这个AuthorizedLimit注解就是一个最简单的注解实现，没有定义任何属性，我们可以看到我们的自定义注解又被注解了@Target(ElementType.METHOD)和@Retention(RetentionPolicy.RUNTIME)这两个元注解，其中@Retention(RetentionPolicy.RUNTIME)是定义注解所必须的
顺带这说一下元注解（meta-annotation）
在JDK 1.5中提供了4个标准的用来对注解类型进行注解的注解类，我们称之为 meta-annotation（元注解），他们分别是：

- @Target
- @Retention
- @Documented
- @Inherited

我们可以使用这4个元注解来对我们自定义的注解类型进行注解，接下来，我们挨个对这4个元注解的作用进行介绍：

- **@Target注解**
Target注解的作用是：描述注解的使用范围（被修饰的注解可以用在什么地方）
Target注解用来说明那些被它所注解的注解类可修饰的对象范围：注解可以用于修饰 packages、types（类、接口、枚举、注解类）、类成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数），在定义注解类时使用了@Target 能够更加清晰的知道它能够被用来修饰哪些对象，它的取值范围定义在ElementType 枚举中

```java
public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration */
    TYPE,                                 // 类、接口、枚举类

    /** Field declaration (includes enum constants) */
    FIELD,                                // 成员变量（包括：枚举常量）

    /** Method declaration */
    METHOD,                               // 成员方法

    /** Formal parameter declaration */
    PARAMETER,                            // 方法参数

    /** Constructor declaration */
    CONSTRUCTOR,                          // 构造方法

    /** Local variable declaration */
    LOCAL_VARIABLE,                       // 局部变量

    /** Annotation type declaration */
    ANNOTATION_TYPE,                      // 注解类

    /** Package declaration */
    PACKAGE,                              // 可用于修饰：包

    /**
     * Type parameter declaration
     *
     * @since 1.8
     */
    TYPE_PARAMETER,                       // 类型参数，JDK 1.8 新增

    /**
     * Use of a type
     *
     * @since 1.8
     */
    TYPE_USE                             // 使用类型的任何地方，JDK 1.8 新增
}
```

- **@Retention注解**
Reteniton注解的作用是：描述注解保留的时间范围（被描述的注解在它所修饰的类中可以被保留到何时）
Reteniton注解用来限定那些被它所注解的注解类在注解到其他类上以后，可被保留到何时，一共有三种策略，定义在RetentionPolicy枚举中

```java
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     */
    SOURCE,  // 源文件保留

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     */
    CLASS,   // 编译期保留，默认值

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME  // 运行期保留，可通过反射去获取注解信息
}
```
可以发现这个枚举类定义了三个值，这三个值分别代表的是我们定义的自定义注解如何保持：

*@Retention(RetentionPolicy.CLASS)修饰的注解，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，但不会被虚拟机读取在运行的时候
*@Retention(RetentionPolicy.SOURCE )修饰的注解,表示注解的信息会被编译器抛弃，不会留在class文件中，注解的信息只会留在源文件中
*@Retention(RetentionPolicy.RUNTIME )修饰的注解，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时

- **@Documented注解**
Documented注解的作用是：描述在使用 javadoc 工具为类生成帮助文档时是否要保留其注解信息，有兴趣的看官自己去研究研究
- **@Inherited注解**
Inherited注解的作用是：使被它修饰的注解具有继承性（如果某个类使用了被@Inherited修饰的注解，则其子类将自动具有该注解），有兴趣的看官自己去研究研究

好了我们回过头来继续演示我们的自定义注解，前文我们自定义来一个最简单的没有定义属性的AuthorizedLimit注解实现，接下来我们做个小测试：

```java
/**
 * FileName: AnnotationTest
 * Author:   frankdevhub@gmail.com
 * Date:     2019-11-02 12:32
 * Description:自定义注解测试类
 */
public class AnnotationTest {

    @AuthorizedLimit()
    public void doSomething(){
        //the function you want to add your custom annotation
    }

    public static void main(String[] args) throws  Exception{
        //获取AnnotationTest这个类的doSomething()方法
        Method method = AnnotationTest.class.getMethod("doSomething", null);

        //判断该方法是否被注解我们的自定义注解：@AuthorizedLimit
        if(method.isAnnotationPresent(AuthorizedLimit.class)){
            System.out.println(method.getAnnotation(AuthorizedLimit.class));
        }
    }
}
```
执行main函数，得到如下效果图

![image](https://user-images.githubusercontent.com/29160332/69005373-596a4780-095c-11ea-8865-c540923e66ee.png)

可以看出来，能打印出这句话证明我们写的确实是一个注解，但是现在我们自定义的注解实现非常简单，我们怎么让注解功能更丰富一些呢？java注解的功能实现基本是通过定义属性实现的（真正实现功能有相关的处理类，处置这些属性，我们先来定义属性）
注解处理器类库(java.lang.reflect.AnnotatedElement)：AnnotatedElement 接口是所有程序元素（Class、Method和Constructor）的父接口，所以程序通过反射获取了某个类的AnnotatedElement对象之后，程序就可以调用该对象的如下四个个方法来访问Annotation信息：

- 方法1：<T extends Annotation> T getAnnotation(Class<T> annotationClass): 返回改程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回null。
- 方法2：Annotation[] getAnnotations():返回该程序元素上存在的所有注解。
- 方法3：boolean is AnnotationPresent(Class<?extends Annotation> annotationClass):判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false.
- 方法4：Annotation[] getDeclaredAnnotations()：返回直接存在于此元素上的所有注释。与此接口中的其他方法不同，该方法将忽略继承的注释。（如果没有注释直接存在于此元素上，则返回长度为零的一个数组。）该方法的调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响

如何给自定义注解增加属性呢？
我们打开Retention注解看看人家是怎么定义属性的：

```java
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Retention {
      /**
       * Returns the retention policy.
       * @return the retention policy
       */
      RetentionPolicy value();
  }
```

这基本就是注解定义属性的方式，类似于java中定义方法，可以设置缺省值，即用注解的时候缺省值是可以不写的
接下来我们也丰富一下我们自己搞的自定义注解AuthorizedLimit，因为我们目的是想实现权限拦截，所以我给自定义注解加了两个属性：


```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizedLimit {

    /** 是否拦截，默认为true */
    boolean limit() default true;

    /** 资源路径，被注解的函数的资源路径 */
    String resourcePath();
}
```

我们再来做个测试，看一下如何获取自定义注解属性：

```java
public class AnnotationTest {

    @AuthorizedLimit(resourcePath = "/pandora/doSomething")
    public void doSomething(){
        //the function you want to add your custom annotation
    }

    public static void main(String[] args) throws  Exception{
        //获取AnnotationTest这个类的doSomething()方法
        Method method = AnnotationTest.class.getMethod("doSomething", null);

        //判断该方法是否被注解我们的自定义注解：@AuthorizedLimit
        if(method.isAnnotationPresent(AuthorizedLimit.class)){
            //获取注解属性
            AuthorizedLimit authorizedLimit = method.getAnnotation(AuthorizedLimit.class);
            String path = authorizedLimit.resourcePath();
            boolean limit = authorizedLimit.limit();
            System.out.println("被AuthorizedLimit注解的方法的资源路径：" + path);
            System.out.println("被AuthorizedLimit注解的方法的是否需要鉴权：" + limit);
        }
    }
}
```

执行main函数，得到控制台如下信息：

![image](https://user-images.githubusercontent.com/29160332/69005394-acdc9580-095c-11ea-82bb-0701950b647d.png)

好了，自定义注解这部分我们说完了，接下来我们做一个注解应用的小示例：基于自定义注解实现简易的系统权限控制
实现的思路是：对我们需要鉴权的资源加入我们自定义的AuthorizedLimit注解，同时写一个SpringMvc拦截器，在此拦截器内部我们获取到方法上是否有注解AuthorizedLimit，如果方法被注解则说明需要鉴权，那么我们获取到自定义注解的资源目录属性，并判断当前登陆用户是否有此资源的权限，如果有则放行，没有则拦截并返回无权限（注意：返回信息要根据是否为Ajax请求进行处理），这样我们就可以自由的在需要进行权限认证的函数上加上@AuthorizedLimit，并指定其value值即可实现该方法的权限控制

我们现有一个系统如下：

![image](https://user-images.githubusercontent.com/29160332/69005403-d695bc80-095c-11ea-82cb-7fee78ad8b9b.png)

假设现在我们想给增加用户按钮设置权限，只有部分用户能进行实际操作，如果有操作权限能顺利创建新用户，没有操作权限提示：您无此操作权限

![image](https://user-images.githubusercontent.com/29160332/69005407-e31a1500-095c-11ea-9bf8-e2111d0664e7.png)

首先我们将上述的自定义注解加到对应的保存用户的方法中：

```java
@RequestMapping("/saveUser")
@ResponseBody
@AuthorizedLimit(resourcePath = "/user/saveUser")
public Map<String, Object> saveUser(User user) {
    //TODO User必填参数检查及是否已存在同名用户
    user.setCreateTime(new Date());
    user.setUpdateTime(new Date());
    int result = userService.saveUser(user);
    Map<String, Object> map = Maps.newHashMap();
    if (result == 1) {
        map.put("code", ResponseCodeEnum.SUCCESS.getValue());
        map.put("msg", ResponseCodeEnum.SUCCESS.getValue());
    } 
    return map;
}
```

接下来我们写一个权限控制拦截器：

```java
/**
 * FileName: AuthorizedInterceptor
 * Author:   frankdevhub@gmail.com
 * Date:     2019-11-02 14:49
 * Description:简易版权限拦截器
 */
@Component
public class AuthorizedInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
              return super.preHandle(request, response, handler);
        }
        HandlerMethod method = (HandlerMethod) handler;
        AuthorizedLimit authorizedLimit = method.getMethodAnnotation(AuthorizedLimit.class);
        if (null != authorizedLimit && authorizedLimit.limit()) {
              //获取登陆人信息，userName系统唯一
              String userName = (String) request.getSession().getAttribute(LoginConstants.PANDORA_LOGIN_KEY);
              //获取资源路径
              String resourcePath = authorizedLimit.resourcePath();
              //因为登陆拦截器在先，所以不必判断userName是否为空
              if (!authorizationHelper.hasResourceAuthorized(userName, resourcePath)) {
              //判断是否为ajax请求,如果是ajax请求则在响应头中设置noAuthorize
              if (isAjax(request)) {
                  response.setHeader("noAuthorize", "true");//在响应头设置无权限
              } else {
                  response.sendRedirect(request.getContextPath() + "/noAuthority");
              }
              return false;
            }
      }
      return super.preHandle(request, response, handler);
  }

    /**
     * 判断是否为Ajax请求
     * @param request HttpServletRequest
     * @return 是否为Ajax请求
     */
    private boolean isAjax(HttpServletRequest request){
        if(null != request && null != request.getHeader("x-requested-with")  &&
                request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            return true;
        }
        return false;
    }
}
```

在AuthorizationHelper中判断用户是否有该资源权限，不同系统设计的用户角色权限管理方式可能不同，你可以将用户角色资源维护在db中也可以维护在其他地方，但归根结底是要拿到用户已授权的资源权限信息进行判断，具体实现细节根据自己喜好来定，这里直接返回false，假定当前登陆用户无权限

```java
/**
 * FileName: AuthorizationHelper
 * Author:   frankdevhub@gmail.com
 * Date:     2019-11-02 14:57
 * Description:权限认证帮助类
 */
@Component
public class AuthorizationHelper {

    public boolean hasResourceAuthorized(String userName, String resourcePath){
        //TODO 获取用户是否有该资源权限
        return false;
    }
}
```

配置拦截器：

```java
/**
 * FileName: WebMvcConfig
 * Author:   frankdevhub@gmail.com
 * Date:     2019-11-02 16:23
 * Description:WebMvc配置类
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Resource
    private AuthorizedInterceptor authorizedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizedInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
```

在前端js文件中加入全部ajax配置：

```javascript
$.ajaxSetup({
    type:'GET' || 'POST',
    complete: function(xhr, textStatus){
        var noAuthorize = xhr.getResponseHeader("noAuthorize");
        if(noAuthorize == 'true') {
            layer.msg("您无此操作权限");
        }
    }
});
```

查看效果：

![image](https://user-images.githubusercontent.com/29160332/69005443-6f2c3c80-095d-11ea-90db-bc4cd7c50ca3.png)

![image](https://user-images.githubusercontent.com/29160332/69005440-63d91100-095d-11ea-8627-2ae26c8681d2.png)

对于非ajax请求，只需要跳转到无权限页面即可
自定义注解的应用有很多，比如我们用上述思路做登陆认证，我们还可以使用SpringAop，利用切面拦截标注了某个自定义注解的函数，并从注解中获取到有关业务属性的值，从而实现织入个性化业务的目的
