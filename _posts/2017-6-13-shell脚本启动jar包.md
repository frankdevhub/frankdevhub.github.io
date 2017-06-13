---  
layout: post  
title:  "Shell脚本启动jar包"  
date:   2017-6-13 10:54:29  
categories: shell  
---  
Linux环境下部署jar包：

shell运行常见错误；

![errortoken](https://cloud.githubusercontent.com/assets/22045217/26352448/6a43082e-3fee-11e7-81eb-bfafc618ece1.PNG)

`1.文本格式转换UNIX否则换行符无法识别`

`2.jar包中最后一行注意空出换行符`

`3.jar包依赖`

`4.JAVA_OPTS相关配置`

-server:一定要作为第一个参数，在多个CPU时性能佳

-Xms：初始Heap大小，使用的最小内存,cpu性能高时此值应设的大一些

-Xmx：Java heap最大值，使用的最大内存

上面两个值是分配JVM的最小和最大内存，取决于硬件物理内存的大小，建议均设为物理内存的一半。

-XX:PermSize:设定内存的永久保存区域

-XX:MaxPermSize:设定最大内存的永久保存区域

-XX:MaxNewSize:

-Xss 15120 这使得JBoss每增加一个线程（thread)就会立即消耗15M内存，而最佳值应该是128K,默认值好像是512k.

+XX:AggressiveHeap 会使得 Xms没有意义。这个参数让jvm忽略Xmx参数,疯狂地吃完一个G物理内存,再吃尽一个G的swap。

-Xss：每个线程的Stack大小

-verbose:gc 现实垃圾收集信息

-Xloggc:gc.log 指定垃圾收集日志文件

-Xmn：young generation的heap大小，一般设置为Xmx的3、4分之一

-XX:+UseParNewGC ：缩短minor收集的时间

-XX:+UseConcMarkSweepGC ：缩短major收集的时间

提示：此选项在Heap Size 比较大而且Major收集时间较长的情况下使用更合适。


shell脚本源码：
<pre class="brush:bash shell;">
		   #!/bin/sh
RUN_NAME="TargetFolder"
echo "RUN_NAME:$RUN_NAME"
MAIN_CLASS="com.fangcs.main.Runtime"
echo "MAIN_CLASS:$MAIN_CLASS"
##JAVA_OPTS="-D$RUN_NAME -server"
##JAVA_OPTS="-cp ./lib/main.jar; com.fangcs.main.Runtime ./lib/function.jar; com.fangcs.function.RunFunction"
JAVA_OPTS=" -jar  ./lib/main.jar "
CLASSPATH=.:./lib/main.jar
echo "CLASSPATH=$CLASSPATH" 
for i in ./lib/*.jar
 do
 CLASSPATH=$CLASSPATH:$i
done
export CLASSPATH

echo "COMPLETE CLASSPATH=$CLASSPATH"
check(){
     ERROR=0;
     if [ "1" -eq $ERROR ]; then
	 echo
	 exit 1
	 fi
}
start(){
    echo "JAVA_HOME=$JAVA_HOME"
	echo "CLASSPATH=$CLASSPATH"
	echo "MAIN_CLASS=$MAIN_CLASS"
	echo "preparing start ..............."
	check || exit 1
	nohup $JAVA_HOME/bin/java $JAVA_OPTS $MAIN_CLASS > ./log/test.log &
	echo "$RUN_NAME started process"
}
stop(){
  echo "Stop process"
  kill -9 `ps -ef|grep $RUN_NAME|grep -v grep|grep -v stop|awk '{print $2}'`
  
 } 
  case "$1" in
     start)
		start
			;;
	 stop)
		stop
			;;
			restart)
			 stop 
			 start
			 ;;
			 *)
			 echo "$Usage: $0(start|stop|restart)"
			 exit 1
			 
			
  esac
  

		   
		   
		</pre>


