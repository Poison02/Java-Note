SpringData操作数据：SpringData：Redis，JDBC，Mybatis，JPA...<br />springboot2.x之后，原来使用的jedis替换成了lettuce<br />jedis：采用的直连，多个线程操作的话，是不安全的，如果想避免不安全的，使用jedis pool连接池，BIO<br />lettuce：采用Netty，实例看可以在多个线程中进行共享，不存在线程不安全的问题，可以减少线程数据，NIO<br />源码分析：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681355685193-ffdffb2f-2fb0-49ec-bb1a-6e8952f5d43b.png#averageHue=%23ebe5d9&clientId=u74117efd-d604-4&from=paste&height=500&id=u00b1e401&name=image.png&originHeight=625&originWidth=1252&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=498112&status=done&style=none&taskId=ufce6ca88-c11a-4e66-99e3-9e7a5ff8017&title=&width=1001.6)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681355713410-573d2391-40f3-40d7-9a32-65eef86e3856.png#averageHue=%23e8e1d5&clientId=u74117efd-d604-4&from=paste&height=210&id=ud75fc621&name=image.png&originHeight=263&originWidth=1188&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=213694&status=done&style=none&taskId=u57cd7161-34ae-4b1f-a32d-3217fea7ea0&title=&width=950.4)<br />1、导入依赖<br />2、配置连接
```xml
spring.redis.host=127.0.0.1
spring.redis.port=6379
```
3、测试<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681355962868-147aa29d-ec96-4d3e-a88b-4945a07948fe.png#averageHue=%23fefdfa&clientId=u74117efd-d604-4&from=paste&height=218&id=uc7bad5fc&name=image.png&originHeight=273&originWidth=621&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=105718&status=done&style=none&taskId=ud670e0a0-b733-4bf6-bce8-3da08705856&title=&width=496.8)<br />在传递对象时，对象需要序列化！<br />详细代码在同目录下的code里。



# 补充

本地连接远程的redis时，需要在远程的 `redis.conf`中做一些修改才能连接上。

```sh
vim redis.conf
```

找到 `daemonize` 改为 `yes`

找到 `bind 127.0.0.1` 将其注释掉，增加 `bind 0.0.0.0`

找到 `protected-mode` 改为 `no`

找到 `requirepass`，解开注释，增加密码

最后保存文件。重新运行`redis-server`

一定要注意！云服务器的防火墙的`6379`端口一定要打开！！！

在服务器上测试redis的，连接上之后都需要 `auth password`进行验证才能使用，本地连接的时候记得配置密码即可。
