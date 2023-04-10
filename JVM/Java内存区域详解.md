<a name="GpmZO"></a>
# 运行时数据区域
Java虚拟机在执行Java程序的过程中会把它管理的内存划分为若干个不同的数据区域。JDK1.8和之前的版本略有不同。<br />**JDK1.8之前：**<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681019133249-7541f9fb-3ec9-473f-b0f4-b34dac354e7d.png#averageHue=%23191919&clientId=u61d18924-675b-4&from=paste&height=736&id=u6bb92b32&name=image.png&originHeight=736&originWidth=698&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79107&status=done&style=none&taskId=ue4d0a1c3-8fd9-48ac-9195-e72b8316e11&title=&width=698)<br />**JDK1.8 及之后**<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681019139228-3cb999b8-f4ad-49ed-9f99-4faf717653e9.png#averageHue=%23181818&clientId=u61d18924-675b-4&from=paste&height=750&id=u0421ce52&name=image.png&originHeight=750&originWidth=730&originalType=binary&ratio=1&rotation=0&showTitle=false&size=71608&status=done&style=none&taskId=u9f623f2a-457e-4240-aed5-bb613c4c42d&title=&width=730)<br />线程私有的：

- 程序计数器
- 虚拟机栈
- 本地方法栈

线程共享的：

- 堆
- 方法区
- 直接内存（非运行时数据区的一部分）

Java虚拟机规范对于运行时数据区域的规定是相当宽松的。以堆为例：堆可以是连续空间，也可以不连续。堆的大小可以固定，也可以在运行时按需扩展。虚拟机实现者可以使用任何垃圾回收算法管理堆，甚至完全不进行垃圾收集也是可以的。
<a name="KJy6w"></a>
## 程序计数器
程序计数器是一块较小的内存空间，可以看作是当前线程所执行的字节码的行号指示器。字节码解释器工作时通过改变这个计数器的值来选取下一条需要执行的字节码指令，分支、循环、跳转、异常处理、线程恢复等功能都需要依赖这个计数器来完成。<br />另外，为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个独立的程序计数器，各线程之间计数器互不影响，独立存储，我们称这类内存区域为“线程私有”的内存。<br />从上面的介绍中我们知道了程序计数器主要有两个作用：

- 字节码解释器通过改变程序计数器来一次读取指令，从而实现代码的流程控制，如：顺序执行、选择、循环、异常处理。
- 在多线程的情况下，程序计数器用于记录当前线程执行的位置，从而当线程被切换回来的时候能够知道该线程上次运行到哪儿了。

注意：程序计数器是唯一不会出现`OutOfMemoryError`的内存区域，它的生命周期随着线程的创建而创建，随着线程的结束而死亡。
<a name="Ic3RG"></a>
## Java虚拟机栈
与程序计数器一样，Java虚拟机栈（后文简称栈）也是线程私有的，它的生命周期和线程相同，随着线程的创建二创建，随着线程的死亡而死亡。<br />张绝对算得上是JVM运行时数据区域的一个核心，除了一些Native方法调用是通过本地方法栈实现（后面会提到），其他所有的Java方法调用都是通过栈来实现的（也需要和其他运行时数据区域比如程序计数器配合）。<br />方法调用的数据需要通过栈进行传递，每一次方法调用都会有一个对应的栈帧被压入栈中，每一个方法调用结束后，都忽悠一个栈帧被弹出。<br />栈是由一个个栈帧组成，而每个栈帧中都拥有：局部变量表、操作数栈、动态链接、方法返回地址。和数据结构上的栈类似，两者都是先进后出的数据结构，只支持出栈和入栈两个操作。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681019955644-6ad766b5-5ea0-4333-947c-7477f7789d87.png#averageHue=%23191919&clientId=u61d18924-675b-4&from=paste&height=561&id=u0cd0282c&name=image.png&originHeight=561&originWidth=347&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23691&status=done&style=none&taskId=u7c3c727f-36ba-4234-8088-af13cc06621&title=&width=347)<br />**局部变量表**主要存放了编译器可知的各种数据类型（boolean，byte，char，short，int，long，float，double）、对象引用（reference类型，它不同于对象本身，可能是一个指指向对象起始地址的引用指针，也可能是指向一个代表对象的句柄或其他与此对象相关的位置）。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681020204649-46636af5-e8cc-4599-94a6-eedc0b2874a1.png#averageHue=%23dee7f2&clientId=u61d18924-675b-4&from=paste&height=369&id=ua78475ea&name=image.png&originHeight=369&originWidth=147&originalType=binary&ratio=1&rotation=0&showTitle=false&size=11668&status=done&style=none&taskId=u4cf3d973-8fc1-48fb-9730-4133ccdc484&title=&width=147)<br />**操作数栈**主要作为方法调用的中转站使用，用于存放方法执行过程中产生的中间计算结果。另外，计算过程中产生的临时变量也会放在操作数栈中。<br />**动态链接**主要服务一个方法需要调用其他方法的场景。Class文件的常量池里保存有大量的符号引用比如方法引用的符号引用。当一个方法要调用其他方法，需要将常量池中只想方法的符号引用转化为其在内存地址中的直接引用。动态链接的作用就是为了将符号引用转换为调用方法的直接引用，这个过程也被称为动态链接。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681020449607-bae30a62-1a18-4832-8e96-b6ce3b4e29a3.png#averageHue=%23f1f0f0&clientId=u61d18924-675b-4&from=paste&height=533&id=ud5064a07&name=image.png&originHeight=533&originWidth=661&originalType=binary&ratio=1&rotation=0&showTitle=false&size=46470&status=done&style=none&taskId=ua288fb19-b980-415c-b579-51c56ee2ee9&title=&width=661)<br />栈空间虽然不是无限的，但一般正常调用的情况下是不会出现问题的。不过，如果函数调用陷入无限循环的话，就会导致栈中被压入太多栈帧而占用太多空间，导致栈空间过深。那么当线程请求栈的深度超过当前Java虚拟机栈的最大深度的时候，就抛出`StackOverFlowError`错误。<br />Java方法有两种返回方式，一种是return语句正常返回，一种是抛出异常。不管哪种返回方式，都会导致栈帧被弹出。也就是说，**栈帧随着方法调用而创建，随着方法结束而销毁。无论方法正常完成还是异常完成都算作方法结束。**<br />除了`StackOverFlowError`错误之外，栈还可能会出现`OutOfMemoryError`错误，这是因为如果站的内存大小可以动态扩展，如果虚拟机在动态扩展栈时无法申请到足够的内存空间，则抛出`OutOfMemoryError`异常。

- 简单总结一下程序运行中栈可能会出现两种错误：<br />`StackOverFlowError`：若栈的内存大小不允许动态扩展，那么当线程请求栈的深度超过当前Java虚拟机栈的最大深度的时候，就抛出`StackOverFlowError`错误。
- `OutOfMemoryError`：如果栈的内存大小可以动态扩展，如果虚拟机在动态扩展栈时无法申请到足够的内存空间，则抛出`OutOfMemoryError`异常。

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681020909179-546cf4a7-da02-4f05-acfd-d75dc75e0acc.png#averageHue=%23e4e4e4&clientId=u61d18924-675b-4&from=paste&height=245&id=u903b55a9&name=image.png&originHeight=245&originWidth=848&originalType=binary&ratio=1&rotation=0&showTitle=false&size=164026&status=done&style=none&taskId=u70aced2b-8698-493f-acca-64aa0336630&title=&width=848)
<a name="BleGn"></a>
## 本地方法栈
和虚拟机栈所发挥的作用非常相似，区别是：**虚拟机栈为虚拟机执行Java方法（也就是字节码）服务，而本地方法栈则为虚拟机使用到的Native方法服务。**在HotSpot虚拟机中和Java虚拟机栈合二为一。<br />本地方法被执行的时候，在本地方法栈也会创建一个栈帧，用于存放该本地方法的局部变量表、操作数栈、动态链接、出口信息。<br />方法执行完毕后响应的栈帧也会出战并释放内存空间，也会出现`StackOverFlowError`和`OutOfMemoryError`两种错误。
<a name="T56rw"></a>
## 堆
Java虚拟机所管理的内存中最大的一块，Java堆是所有线程共享的一块内存区域，在虚拟机启动时创建。**此内存区域的唯一目的就是存放对象实例，几乎所有的对象实例以及数组都在这里分配内存。**<br />骄傲v啊世界中“几乎”所有的对象都在堆中分配，但是，随着JIT编译器的发展与逃逸分析技术逐渐成熟，栈上分配、标量替换优化技术将会导致一些微妙的变化，所有的对象都分配到堆上也渐渐变得不那么“绝对”了。从JDK1.7开始已经默认开启逃逸分析，如果某些方法中的对象引用没有被返回或者未被外面使用（也就是未逃逸出去），那么对象可以直接在栈上分配内存。<br />Java堆是垃圾收集器管理的主要区域，因此也被称作**GC堆（Garbage Collected Heap）**。从垃圾回收的角度，由于现在收集器基本都采用分代垃圾收集算法，所以Java堆还可以细分为：新生代和老年代；再细致一点有：Eden、Survivor、Old等空间。进一步划分的目的是更好的回收内存，或者更快地分配内存。<br />在JDK版本及JDK7版本之前，堆内存被通常分为下面单个部分：

1. 新生代内存（Young Generation）
2. 老生代（Old Generation）
3. 永久代（Permanent Generation）

下图所示的Eden区、两个Survivor区S0和S1都属于新生代，中间一层属于老年代，最下面一层属于永久代。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681021777902-cdc0bdd3-1d84-4723-ab64-ef77c11f2db1.png#averageHue=%23151515&clientId=u61d18924-675b-4&from=paste&height=610&id=uc6b1354f&name=image.png&originHeight=610&originWidth=1141&originalType=binary&ratio=1&rotation=0&showTitle=false&size=46647&status=done&style=none&taskId=ua7c27a9e-67ae-4c2f-9ba3-cd5035c8846&title=&width=1141)<br />**JDK8之后PermGen（永久）已被MetaSpace（元空间）取代，元空间使用的是本地内存。**
>  大部分情况，对象都会首先在 Eden 区域分配，在一次新生代垃圾回收后，如果对象还存活，则会进入 S0 或者 S1，并且对象的年龄还会加 1(Eden 区->Survivor 区后对象的初始年龄变为 1)，当它的年龄增加到一定程度（默认为 15 岁），就会被晋升到老年代中。对象晋升到老年代的年龄阈值，可以通过参数 -XX:MaxTenuringThreshold 来设置。

修正： “Hotspot 遍历所有对象时，按照年龄从小到大对其所占用的大小进行累积，当累积的某个年龄大小超过了 survivor 区的一半时，取这个年龄和 MaxTenuringThreshold 中更小的一个值，作为新的晋升年龄阈值”。<br />动态年龄计算的代码如下：
```java
uint ageTable::compute_tenuring_threshold(size_t survivor_capacity) {
	//survivor_capacity是survivor空间的大小
size_t desired_survivor_size = (size_t)((((double) survivor_capacity)*TargetSurvivorRatio)/100);
size_t total = 0;
uint age = 1;
while (age < table_size) {
total += sizes[age];//sizes数组是每个年龄段对象大小
if (total > desired_survivor_size) break;
age++;
}
uint result = age < MaxTenuringThreshold ? age : MaxTenuringThreshold;
	...
}

```
堆这里最容易出现的就是`OutOfMemoryError`错误，并且出现这种错误之后的表现形式还会有几种，比如：

1. `java.lang.OutOfMemoryError: GC Overhead Limit Exceeded`：当JCM花太多时间执行垃圾回收并且只能回收很少的对空间时，就会发生此错误。
2. `java.lang.OutOfMemoryError: Java heap space`：假如在创建新的对象时，堆内存中的空间不足以存放新创建得对象，就会引发此错误。（和配置得最大堆内存有关，且受制于物理内存大小。最大堆内存可通过`-Xmx`参数配置，若没有特别配置，将会使用默认值）
3. ...
<a name="aJlTy"></a>
## 方法区
方法区属于是JVM运行时数据区域的一块逻辑区域，是各个线程共享的内存区域。<br />《Java虚拟机规范》只是规定了有方法区这么个概念个它的作用，方法区到底要如何实现那就是虚拟机自己要考虑的事情了。也就是说，在不同的虚拟机实现上，方法区的实现是不同的。<br />当虚拟机要使用一个类时，他需要读取并解析Class文件获取相关信息，再将信息存入到方法区。方法区会存储已被虚拟机加载的**类信息、字段信息、方法信息、常量、静态变量、即时编译器编译后的代码缓存等数据。**<br />**方法区和永久代以及元空间是什么关系呢？**方法区和永久代以及元空间的关系很像Java中接口和类的关系，类实现了接口，这里的类就可以看作是永久代和元空间，接口可以看作是方法区，也就是说永久代以及元空间是HotSpot虚拟机对虚拟机规范中方法区的两种实现方式。并且，永久代是JDK1.8之前的方法区实现，JDK1.8及以后方法区的实现变成了元空间。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681103328198-a4668a0d-299b-41b0-8eb0-f00c7b491dc2.png#averageHue=%23151515&clientId=u48f26cbc-9a9a-4&from=paste&height=441&id=u8a6e4157&name=image.png&originHeight=441&originWidth=628&originalType=binary&ratio=1&rotation=0&showTitle=false&size=21358&status=done&style=none&taskId=u0c270ad1-56af-4c96-af44-a3396bd4e2f&title=&width=628)<br />**为什么要将永久代（PermGen）替换成元空间（MetaSpace）呢？**<br />下图来自《深入理解Java虚拟机》第三版2.2.5：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681103380054-d9f54cf0-ffd7-4114-bf92-7999c0984c14.png#averageHue=%23dfcecc&clientId=u48f26cbc-9a9a-4&from=paste&height=138&id=u33ce11f3&name=image.png&originHeight=138&originWidth=838&originalType=binary&ratio=1&rotation=0&showTitle=false&size=97330&status=done&style=none&taskId=u5d6b5fa9-dd4b-4271-b483-782b7cbdda7&title=&width=838)

1. 整个永久代有一个JVM本身设置的固定大小上限，无法进行调整，而元空间使用的是本地内存，受本机可用内存的限制，虽然元空间仍旧可能溢出，但是比原来出现的几率会更小。
> 当元空间溢出时会得到如下错误：`java.lang.OutOfMemoryError: MetaSpace`

你可以使用`-XX: MetaspaceSize`标志设置最大元空间大小，默认值为unlimited，这意味着他只受系统内存的限制，`-XX: MetaspaceSize`调整标志定义元空间的初始大小如果未指定此标志，则`Metaspace`将根据运行时的应用程序需求动态地重新调整大小。

2. 元空间里面存放的是类的元数据，这样加载多少泪的元数据就不由`MaxPermSize`控制了，而由系统的实际可用空间来控制，这样能加载的类就更多了。
3. 在JDK8，合并HotSpot和JRockit的代码时，JRockit从来没有一个叫永久代的东西，合并之后就没有币摇额外的设置这么一个永久代的地方了。
<a name="jL2ib"></a>
### 方法区常用参数有哪些？
JDK1.8之前永久代还没被彻底移除的时候通常通过下面这些参数来调节方法区大小：
```shell
-XX:PermSize=N //方法区 (永久代) 初始大小
-XX:MaxPermSize=N //方法区 (永久代) 最大大小,超过这个值将会抛出 OutOfMemoryError 异常:java.lang.OutOfMemoryError: PermGen

```
相对而言，垃圾收集行为在这个区域是比较少出现的，但并非数据进入方法区后就”永久存在“了。<br />JDK1.8的时候，方法区（HotSpot的永久代）被彻底移除了（JDK1.7就已经开始了），取而代之的是元空间，元空间使用的是本地内存。下面是一些常用参数：
```java
-XX:MetaspaceSize=N //设置 Metaspace 的初始（和最小大小）
-XX:MaxMetaspaceSize=N //设置 Metaspace 的最大大小

```
与永久代很大的不同就是，如果不指定大小的话，随着更多类的创建，虚拟机会耗尽所有可用的系统内存。
<a name="ORUdS"></a>
## 运行时常量池
Class文件中除了有类的版本、字段、方法、接口等描述信息外，还有用于存放编译期生成的各种字面量（Literal）和符号引用（Symbolic Reference）的**常量池表（Constant Pool Table）。**<br />字面量是源代码中的固定值的表示法，即通过字面我们就能知道其值的含义。字面量包括证书、浮点数和字符串字面量。常见的符号引用包括符号引用、字段符号引用、方法符号引用、接口方法符号。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681104011724-8a17f0fa-6b34-45df-9def-ef8cd0403100.png#averageHue=%23eae4e3&clientId=u48f26cbc-9a9a-4&from=paste&height=378&id=ub1ce3f7c&name=image.png&originHeight=378&originWidth=682&originalType=binary&ratio=1&rotation=0&showTitle=false&size=210730&status=done&style=none&taskId=u00eac974-dbc8-4a0d-9457-f55a8a189be&title=&width=682)<br />常量池表会在类加载后存放到方法区的运行时常量池中。<br />运行时常量池的功能类似于传统编程语言的符号表，尽管他包含了比典型符号表更广泛的数据。<br />既然运行时常量池是方法区的一部分，自然收到方法区内存的限制，当常量池无法再申请到内存时会抛出`OutOfMemoryError`错误。
<a name="RGmXl"></a>
## 字符串常量池
**字符串常量池**是JVM为了提升性能和减少内存消耗针对字符串（String类）专门开辟的一块区域，主要目的是为了避免字符串的重复创建。
```java
// 在堆中创建字符串对象”ab“
// 将字符串对象”ab“的引用保存在字符串常量池中
String aa = "ab";
// 直接返回字符串常量池中字符串对象”ab“的引用
String bb = "ab";
System.out.println(aa==bb);// true

```
HotSpot虚拟机中字符串常量池的实现是`src/hotspot/share/classfile/stringTable.cpp`**，**`StringTable`本质上就是一个`HashSet<String>`**，**容量是`StringTableSize`（可以通过`-XX:StringTableSize`参数来设置）。<br />`StringTable`**中保存的是字符串对象的引用，字符串对象的引用指向堆中的字符串对象。**<br />JDK1.7之前，字符串常量池存放在永久代。JDK1.7字符串常量池和静态变量从永久代移到了Java堆中。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681104535495-bec9e17e-2859-4ccd-a6d0-e5a9f6e17946.png#averageHue=%23161616&clientId=u48f26cbc-9a9a-4&from=paste&height=619&id=u616c494c&name=image.png&originHeight=619&originWidth=832&originalType=binary&ratio=1&rotation=0&showTitle=false&size=38478&status=done&style=none&taskId=u4041346e-3190-4df8-9e39-646f86d7de5&title=&width=832)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681104631492-2f8bcf2c-7178-4153-8bc7-6ef5b5b7e862.png#averageHue=%23161616&clientId=u48f26cbc-9a9a-4&from=paste&height=606&id=ua3e94571&name=image.png&originHeight=606&originWidth=813&originalType=binary&ratio=1&rotation=0&showTitle=false&size=38621&status=done&style=none&taskId=uc35a8f5b-38a8-44b1-acfb-e3574dcbbb6&title=&width=813)<br />**JDK1.7为什么要将字符串常量池移动到堆中？**<br />主要是因为永久代（方法区实现）的GC回收效率太低，只有在整堆收集（Full GC）的时候才会被执行GC。Java程序中通常会有大量的被创建的字符串等待回收，将字符串常量池放到堆中，能够能高效及时地回收字符串内存。
<a name="I8o9S"></a>
## 直接内存
直接内存是一种特殊地内存缓冲区，并不在Java堆或方法区中分配的，而是通过JNI地方式在本地内存上分配的。<br />直接内存并不是虚拟机运行时数据区地一部分，也不是虚拟机规范中定义的内存区域，但是这部分内存也被频繁地使用。而且也可能导致`OutOfMemoryError`错误出现。<br />JDK1.4中新加入的**NIO（NIO-Blocking I/O，也被称为New I/O），**引入了一种基于**通信（Channel）与缓存区（Buffer）地I/O方式，它可以直接使用Native函数库直接分配堆外内存，然后通过一个存储在Java堆中地DirectByteBuffer对象作为这块内存地引用进行操作。这样就能在一些场景中显著提高性能，因为避免了在Java堆和Native堆之间来回复制数据。**<br />直接内存地分配不会受到Java堆地限制，但是，既然是内存就会收到本机总内存大小以及处理器寻址空间地限制。<br />类似地概念还有**堆外内存。**<br />堆外内存就是把内存对象分配在堆（新生代+老年代+永久代）以外地内存，这些内存直接受操作系统管理（而不是虚拟机），这样做的结果就是能够在一定程度上减少垃圾回收对应用程序造成的影响。
<a name="NSszA"></a>
# HotSpot虚拟机对象探秘
通过上面地介绍我们大概知道了虚拟机地内存情况，下面我们来详细地了解一下HotSpot虚拟机在Java堆中对象分配、布局和访问地全过程。
<a name="UUqHb"></a>
## 对象的创建
Java对象的创建过程！！！
<a name="VibOR"></a>
### Step1：类加载检查
虚拟机遇到一条new指令时，首先将去检查这个指令的参数是否能在常量池中定位到这个类的符号引用，并且检查这个符号引用代表的类是否已被加载过、解析和初始化过。如果没有，那必须先执行相应的类加载过程。
<a name="SQxmW"></a>
### Step2：分配内存
在**类加载检查**通过后，接下来虚拟机将为新生代对象**分配内存。**对象所需的内存大小在类加载完成后便可确定，为对象分配空间的任务等同于把一块确定大小的内存从Java堆中划分出来。**分配方式**有**”指针碰撞“和”空闲列表“**两种，**选择哪种分配方式由Java堆是否规整决定，而Java堆是否规整又由所采用的垃圾收集器是否带有压缩整理功能决定。**<br />**内存分配的两种方法：**

- 指针碰撞
   - 使用场景：堆内存规整（即没有内存碎片）的情况下。
   - 原理：用过的内存全部整合到一边，没有用过的内存放在另一边，中间有一个分界指针 ，只需要向着没用过的内存方向向该指针移动对象大小位置即可。
   - 使用该分配方式的GC收集器：Serial、PerNew
- 空闲列表：
   - 使用场景：堆内存不规整的情况下。
   - 原理：虚拟机会维护一个列表，该列表中会记录哪些内存块是可用的，再分配的时候，找一块足够大的内存块来划分给对象实例，最后更新列表记录。
   - 使用该分配方式的GC收集器：CMS

选择以上两种方式中的哪一种，取决于Java堆内存是否规整。而Java堆内存是否规整，取决于GC收集器的算法是”标记-清除”，还是“标记-整理”（也称作标记-压缩），值得注意的是，复制算法内存也是规整的。<br />**内存分配并发问题**<br />在创建对象的时候有一个很重要的问题，就是线程安全，因为在实际开发过程中，创建对象是很频繁的事情，作为虚拟机来说，必须要保证线程是安全的，通常来讲，虚拟机采用两种方式来保证线程安全：

- **CAS+失败重试：**CAS是乐观锁的一种实现方式。所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就冲是，直到成功为止。**虚拟机采用CAS配上失败重试的方式保证更新操作的原子性。**
- **TLAB：**为每个线程与现在Eden区分配一块内存，JVM在给线程中的对象分配内存时，首先在TLAB分配，当对象大于TLAB中的剩余内存或TLAB的内存已用尽时，再采用上述的CAS进行内存分配。
<a name="ZgV8p"></a>
### Step3：初始化零值
内存分配完成后，虚拟机需要将分配到的内存空间都初始化为零值（不包括对象头），这一步操作保证了对象的实例字段在Java代码中可以不赋初始值就直接使用，程序能访问到这些字段的数据类型锁对象的零值。
<a name="wNdjD"></a>
### Step4：设置对象头
初始化零值完成之后，**虚拟机要对对象进行必要的设置，**例如这个对象是哪个类的实例、如何才能找到类的元数据信息、对象的哈希码、对象的GC分代年龄等信息。**这些信息存放在对象头中**。另外，根据虚拟机当前运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。
<a name="OmeaO"></a>
### Step5：执行init方法
在上面工作都完成之后，从虚拟机的视角来看，一个新的对象都已经产生了，但从Java程序的视角来看，对象创建才刚开始`<init>`方法还没有执行，所有的字段都还为零。所以一般来说，执行new指令之后会接着执行`<init>`方法，把对象按照程序员的意愿进行初始化，这样一个真正可用的对象才算完全产生出来。
<a name="r2mE1"></a>
## 对象的内存布局
在HotSpot虚拟机中，对象在内存中的布局可以分为3块区域：**对象头、示例数据和对齐填充。**<br />**HotSpot虚拟机的对象头包括两部分信息，第一部分用于存储对象自身的运行时数据（**哈希码、GC分代年龄、锁状态标志等等**），另一部分是类型指针，**即对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例。<br />**实例数据部分是对象真正存储的有效信息，**也是在程序中所定义的各种类型的字段内容。<br />**对齐填充部分不是必然存在的，也没有什么特别的含义，仅仅起占位作用。**因为HotSPot虚拟机的自动内存管理系统要求对象起始地址必须是8字节的整数倍，换句话说就是对象的带线啊哦必须是8字节的整数倍。而对象头部分正好是8字节的倍数（1倍或2倍），因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。
<a name="Ycu5E"></a>
## 对象的访问定位
建立对象就是为了适用对象，我们的Java程序通过栈上的reference数据来操作堆上的具体对象。对象的访问方式由虚拟机实现而定，目前主流的访问方式有：**使用句柄、直接指针。**
<a name="QJP9m"></a>
### 句柄
如果使用句柄的话，那么Java堆中将会划分出一块内存来做为句柄池，reference中存储的就是对象的句柄地址，而句柄中包含了对象实例数据与对象类型数据各自的具体地址信息。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681109090345-12733354-9731-40c3-914e-06bdf6ac9ff5.png#averageHue=%23181818&clientId=u48f26cbc-9a9a-4&from=paste&height=598&id=ub5d73f0b&name=image.png&originHeight=598&originWidth=932&originalType=binary&ratio=1&rotation=0&showTitle=false&size=62700&status=done&style=none&taskId=u97d5deb4-95a2-4a7e-9ad2-b882f127268&title=&width=932)
<a name="VYYzF"></a>
### 直接指针
如果使用直接指针访问，reference中存储的直接就是对象的地址。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681109146488-58d4153e-d967-40fe-9d29-ed32d830fa6b.png#averageHue=%23171717&clientId=u48f26cbc-9a9a-4&from=paste&height=605&id=u7dac4037&name=image.png&originHeight=605&originWidth=951&originalType=binary&ratio=1&rotation=0&showTitle=false&size=56715&status=done&style=none&taskId=ue3cd6352-54c1-45fb-9860-a11b28c2adb&title=&width=951)<br />这两种对象访问方式各有优势。使用句柄来访问的最大好处就是reference中存储的是稳定的句柄地址，在对象被移动时指挥改变句柄中的实例数据指针，而reference本色不需要修改。使用直接指针访问方式最大的好处就是速度快，他节省了一次指针定位的时间开销。<br />HotSpot虚拟机主要使用的就是这种方式来进行对象访问。

