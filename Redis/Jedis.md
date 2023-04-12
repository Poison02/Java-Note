<a name="usQ7C"></a>
# 使用Java操作Redis！
什么是Jedis？Redis官方推荐的Java连接开发工具，使用Java操作Redis中间件。
<a name="bt5Fn"></a>
## 1、Java新建maven项目
<a name="XA1G7"></a>
## 2、导入对应的依赖
```xml
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

<!--    导入Jedis和fastjson-->
    <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
    <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
      <version>3.2.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2 -->
    <dependency>
      <groupId>com.alibaba.fastjson2</groupId>
      <artifactId>fastjson2</artifactId>
      <version>2.0.23</version>
    </dependency>

</dependencies>
```
<a name="XOHSZ"></a>
## 3、编码测试
```java
public class TestPing {

    public static void main(String[] args) {
        // 这里测试Redis连接，并发送Ping命令
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        System.out.println(jedis.ping());
    }

}
```
输出`PONG`就表示连接成功！<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681300708700-b7b4379f-4222-4445-a1f2-e3856a17e68a.png#averageHue=%2322272d&clientId=u4ad98380-16e8-4&from=paste&height=95&id=u8bb7a81c&name=image.png&originHeight=95&originWidth=360&originalType=binary&ratio=1&rotation=0&showTitle=false&size=5598&status=done&style=none&taskId=u7bfb476c-e535-41ed-9537-3c429b02c54&title=&width=360)
<a name="IfVxj"></a>
# 常用API
String<br />List<br />Set<br />Hash<br />Zset<br />这里就不写测试了，之前在xshell里面都写过，一样的命令，只需要调用api即可。<br />对事务的操作：
```java
public class TestTransaction {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "zch");
        String user1 = jsonObject.toJSONString();
        String user2 = jsonObject.toJSONString();

        // 开启事务
        jedis.select(2);
        Transaction multi = jedis.multi();
        try {
            multi.set("user1", user1);
            multi.set("user2", user2);
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }

    }

}
```
