<a name="loF4I"></a>
## 什么是序列化和反序列化？
如果我们需要持久化Java对象比如将Java对象保存在文件中，或者在网络上传输Java对象，这些场景都需要用到序列化。<br />简单来说：

- **序列化：**将数据结构或对象转换成二进制字节流的过程。
- **反序列化：**将在序列化过程中所生成的二进制字节流转换成数据结构或者对象的过程。

对于Java来说，我们序列化的都是对象（Object）也就是实例化后的类（class）。
<a name="BQJVj"></a>
### 序列化和反序列化常见应用场景

- 对象在进行网络传输（比如远程方法调用 RPC 的时候）之前需要先被序列化，接收到序列化的对象之后需要再进行反序列化；
- 将对象存储到文件之前需要进行序列化，将对象从文件中读取出来需要进行反序列化；
- 将对象存储到数据库（如 Redis）之前需要用到序列化，将对象从缓存数据库中读取出来需要反序列化；
- 将对象存储到内存之前需要进行序列化，从内存中读取出来之后需要进行反序列化。

综上：**序列化的主要目的是通过网络传输对宪法或者说是将对象从存储到文件系统、数据库、内存中。**<br />![](https://cdn.nlark.com/yuque/0/2023/png/35204765/1676707630129-6e8911da-c93e-483d-b29f-8832f9a8d56a.png#averageHue=%23fefefe&clientId=u34fceb7c-a140-4&from=paste&id=ufeb026ae&originHeight=266&originWidth=664&originalType=url&ratio=1.25&rotation=0&showTitle=false&status=done&style=none&taskId=uebe3441e-eaa3-42e8-a269-c5ec8a450e5&title=)
<a name="LgOcX"></a>
### 序列化协议对应与 TCP/IP 4层模型的哪一层？
我们知道 TCP/IP 四层模型是下面这样的，

1. 应用层
2. 传输层
3. 网络层
4. 网络接口层

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1676708270614-7ad5a784-bde6-4221-8ece-f8627ccaf6f6.png#averageHue=%231c1c1c&clientId=u34fceb7c-a140-4&from=paste&height=410&id=uf855c491&name=image.png&originHeight=512&originWidth=867&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=72187&status=done&style=none&taskId=u90d7f192-99b4-43b3-8ef8-5a1d3f8b795&title=&width=693.6)<br />如图所示，OSI七层协议中，表示层主要就是对应用层的用户数据进行处理转换为二进制流，反过来的话，就是将二进制流转换成应用层的用户数据。<br />所以序列化协议属于TCP/IP协议应用层的一部分。
<a name="w9UTm"></a>
## 常见序列化协议有哪些？
JDK 自带的序列化方式一般不会用，因为效率低且存在安全问题。比较常见的序列化协议有Hession、Kryo、Protobuf、ProtoStuff，这些就是基于二进制的序列化协议。<br />像 JSON 和 XML 这种属于文本类序列化方式。虽然可读性比较好，但是性能较差，一般不会选择。
<a name="oQbLy"></a>
## JDK自带的序列化方式
使用JDK自带的序列化，只需要实现 _java.io.Serializable_ 接口即可。
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private RpcMessageTypeEnum rpcMessageTypeEnum;
}
```
<a name="zAW6A"></a>
### serialVersionUID 有什么作用？
序列化号 _serialVersionUID_ 属于版本控制的作用。反序列化时，会检查 _serialVersionUID_ 是否和当前类的 _serialVersionUID_ 一致。如果不一致，则会抛出 _InvalidClassException _异常。强烈推荐每个序列化类都手动指定其 _serialVersionUID_，如果不手动指定，那么编译器会动态生成默认的 _serialVersionUID_。
<a name="m2RDC"></a>
### serialVersionUID不是被static变量修饰了吗？为什么还会被序列化？
static修饰的变量是静态变量，位于方法区，本身是不会被序列化的。static变量是属于类的而不是对象。反序列之后，static变量的值就像是默认赋予给了对象一样，看着就像是`static`变量被序列化，实际是假象。
<a name="JIvWx"></a>
### 如果有些字段不想进行序列化怎么办？
对于不想进行序列化的变量，可以使用 `transient`** **进行修饰。<br />`transient`关键字的作用是：阻止实例中那些用词关键字修饰的变量序列化；当对象被反序列化时，被`transient`修饰的变量值不会被持久化和恢复。<br />关于`transient`还有几点需要注意：

- `transient`只能修饰变量，不能修饰类和方法。
- `transient`修饰的变量，在反序列化后变量值将会被置成类型的默认值。例如，如果是修饰`int`类型，那么反序列化后结果就是`0`。
- `static`变量因为不属于任何对象（Object），所以无论又没有`transient`关键字修饰，均不会被序列化。
<a name="gsk2D"></a>
### 为什么不推荐使用JDK自带的序列化？
很少或者说几乎不会直接使用JDK自带的序列化方式，主要原因有下面这些原因：

- **不支持跨语言调用：**如果调用的是其他语言开发的服务的时候就不支持了。
- **性能差：**相比于其他序列化框架性能更低，主要原因是序列化之后的字节数组体积较大，导致传输成本加大。
- **存在安全问题：**序列化和反序列化本身并不存在问题。但当输入的反序列化的数据可被用户控制，那么攻击这即可通过构造而恶意输入，让反序列化产生非预期的对象，在此过程中执行构造的任意代码。
<a name="OU7Tn"></a>
## Kryo
Kryo是一个高性能的序列化/反序列化工具，由于其边长存储特性并使用了字节码生成机制，拥有较高的运行速度和较小的字节码体积。<br />`[guide-rpc-framework](https://github.com/Snailclimb/guide-rpc-framework)`就是使用的Kryo进行序列化，相关代码如下：
```java
/**
 * Kryo serialization class, Kryo serialization efficiency is very high, but only compatible with Java language
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 19:29:00
 */
@Slf4j
public class KryoSerializer implements Serializer {

    /**
     * Because Kryo is not thread safe. So, use ThreadLocal to store Kryo objects
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException("Serialization failed");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对象
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializeException("Deserialization failed");
        }
    }

}

```
GitHub 地址：[https://github.com/EsotericSoftware/kryo](https://github.com/EsotericSoftware/kryo)
<a name="CLFCw"></a>
## Protobuf
Protobuf 出自于Google，性能比较优秀，也支持多种语言，同时还是跨平台的。就是在使用中过于繁琐。但是这也没有序列化漏洞的风险。<br />一个简单得分proto文件如下：
```protobuf
// protobuf的版本
syntax = "proto3";
// SearchRequest会被编译成不同的编程语言的相应对象，比如Java中的class、Go中的struct
message Person {
  //string类型字段
  string name = 1;
  // int 类型字段
  int32 age = 2;
}
```
GitHub：[https://github.com/protocolbuffers/protobuf](https://github.com/protocolbuffers/protobuf)
<a name="UIqYy"></a>
## ProtoStuff
由于Protobuf的易用性，ProtoStuff诞生了。<br />基于Google Protobuf，提供了更多的功能和更简易的用法。虽然更加医用，但不代表性能更差。<br />Github：[https://github.com/protostuff/protostuff](https://github.com/protostuff/protostuff)
<a name="ZLCpq"></a>
## Hessian
这是一个更加轻量级的，自定义描述的二进制RPC协议。Hessian是一个比较老的序列化实现了，并且同样也是跨语言的。
<a name="oipej"></a>
## 总结
Kryo是专门针对Java语言序列化方式并且性能非常好，如果你的应用是专门针对Java语言的话可以考虑使用。
