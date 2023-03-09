<a name="oIMBT"></a>
# Map接口
<a name="vh60D"></a>
## HashMap和Hashtable的区别

- **线程是否安全：**`HashMap`是非线程安全的，`Hashtable`是线程安全的，因为`Hashtable`内部的方法基本都经过`synchronized`修饰。（如果你要保证线程安全的话就使用`ConcurrentHashMap`吧！）
- **效率：**因为线程安全的问题，`HashMap`要比`Hashtable`效率高一点。此外，`Hashtable`基本被淘汰，尽量别使用。
- **对Null Key和Null value的支持：**`HashMap`可以存储null的key和value，但null作为键只能有一个，null作为值可以有多个；`Hashtable`不允许有null的key和value，否则会抛出`NullPointException`。
- **初始容量大小和每次扩容容量大小的不同：**①创建时如果不指定容量初始值，`Hashtable`默认的初始大小为11，之后每次扩容，容量变为原来的2n + 1。`HashMap`默认的初始化大小为16，之后每次扩容，容量变为原来的2倍。②创建时如果给定了容量初始值，那么`Hashtable`会直接使用你给定的大小，而`HashMap`会将其扩容为2的幂次方大小（`HashMap`中的`tableSizeFor()`方法保证，下面给出了源代码）。也就是说`HashMap`总是使用2的幂作为哈希表的大小。
- **底层数据结构：**JDK1.8以后的`HashMap`在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）时，将链表转化为红黑树（将链表转换为红黑树前会判断，如果当前数组的长度小于64，那么会选择先进行数组扩容，而不是转换为红黑树），以减少搜索时间。`Hashtable`没有这样的机制。
<a name="JFdUK"></a>
## HashMap和HashSet区别
`HashSet`底层是基于`HashMap`实现的。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677378909466-500c8e07-1158-4af2-88be-d73457b53690.png#averageHue=%23262629&clientId=u487300b5-ec6a-4&from=paste&height=251&id=u5b8d1b65&name=image.png&originHeight=251&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=28380&status=done&style=none&taskId=u3c5fa8a7-4e4d-4708-8cfe-9cfd4560073&title=&width=868)
<a name="r1UPK"></a>
## HashMap和TreeMap区别
`TreeMap`和`HashMap`都继承自`AbstractMap`，但是需要注意的是`TreeMap`它还实现了`NavigableMap`接口和`SortedMap`接口。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677379014015-0f8982f7-58d8-45fa-b8a8-1e55ced993b5.png#averageHue=%23302f2e&clientId=u487300b5-ec6a-4&from=paste&height=266&id=u83ba2c00&name=image.png&originHeight=266&originWidth=646&originalType=binary&ratio=1&rotation=0&showTitle=false&size=38572&status=done&style=none&taskId=u10656610-a4da-4571-a9cc-1b0f2bc23cb&title=&width=646)<br />实现`NavigableMap`接口让`TreeMap`有了对集合内元素的搜索的能力。<br />实现`SortedMap`接口让`TreeMap`有了对集合中的元素根据键排序的能力。默认是按key的升序排序，不过我们也可以指定排序的比较器。<br />**相比于**`**HashMap**`**来说**`**TreeMap**`**主要多了对集合的元素根据排序的能力以及对集合内元素的搜索的能力。**
<a name="YWC7v"></a>
## HashSet如何检查重复
在JDK1.8中，`HashSet`的`add()`方法知识简单的调用了`HashMap`的`put()`的方法，并且判断了一下返回值以确保是否有重复元素。`HashSet`中的源码：
```java
// Returns: 当 set 中没有包含 add 的元素时返回真
public boolean add(E e){
    return map.put(e, PRESENT) == null;
}
```
在`HashMap`中的`putVal()`方法中也能看到：
```java
// Returns：如果插入位置没有元素返回null，否则返回上一个元素
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict){
}
```
也就是说，在JDK1.8中，实际上无论`HashSet`中是否已经存在了某元素，`HashSet`都会直接插入，只是会在`add()`方法的返回值告诉我们插入前是否存在相同元素。
<a name="phICb"></a>
## HashMap的底层实现
<a name="DqAmc"></a>
### JDK1.8之前
JDK1.8之前`HashMap`底层是 **数组加链表 **结合在一起使用也就是 **链表散列 **。HashMap是通过key的`hashcode`经过扰动函数处理后得到hash值，然后通过`(n - 1) & hash`判断当前元素存放的位置（这里的n指的是数组的长度），如果当前位置存在元素的话，就判断该元素与要存入的而元素的hash值以及key是否相同，如果相同，直接覆盖，不相同就通过拉链法解决冲突。<br />所谓扰动函数指的就是`HashMap`的`hash`方法。使用`hash`方法也就是扰动函数是为了放置一些实现比较差的`hashCode()`方法，换句话说扰动函数就是为了减少碰撞。
<a name="oWg29"></a>
#### JDK1.8中HashMap的hash方法源码：
JDK1.8中的 hash 方法相比于JDK1.7 hash 方法更加简化，但是原理不变。
```java
static final int hash(Object key){
    int h;
	// key.hashCode(), 返回散列值也就是hashCode
	// ^ 按位异或
	// >>> 无符号右移，忽略符号位，空位都以0补齐
	return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
对比一下JDK1.7中的HashMap的hash方法源码：
```java
static int hash(int h){
    h ^= (h >>> 20) ^ (h >>> 12);
	return h ^ (h >>> 7) ^ (h >>> 4);
}
```
所谓“拉链法”就是：将链表和数组相结合。也就是说创建一个链表数组，数组中每一个就是一个链表，若遇到哈希冲突，则将冲突的值加到链表中即可。
<a name="mLDKo"></a>
### JDK1.8之后
相比于之前的版本，JDK1.8之后在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）（将链表转换为红黑树前会判断，如果当前数组的长度小于64，那么会选择先进性数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间。
<a name="td3Ej"></a>
## HashMap的长度为什么是2的幂次方？
为了能让`HashMap`存取高效，尽量减少碰撞，也就是要尽量把数据分配均匀。Hash值的范围值（-2147483648~2147483647），只要哈希函数映射的比较均匀松散，一般应用是很难出现碰撞的。但是这么大的内存，数组是放不下的。所以这个散列值是不能直接拿来用的。用之前还要先做对数组的长度取模运算，得到的余数才能用来要存放的位置也就是对应的数组下标。这个数组下标的计算方法是 “(n - 1) & hash”。（n代表数组长度）。这就解释了为啥是2的幂次方。<br />**这个算法应该如何设计呢？**<br />我们首先可能想到采用 % 取余的操作来实现。但是 “**取余（%）操作中如果除数是2的幂次等价于与其除数减一的与（&）操作（也就是说 hash % length == hash & (length - 1)的前提是length是2的n次方）。**”并且 **采用二进制位操作&，相当于 % 能够提高运算效率，这就解释了 HashMap的长度为什么是2的幂次方。**
<a name="lbk7F"></a>
## HashMap多线程操作导致死循环问题
主要原因在于并发下的 Rehash 会造成元素之间会形成一个循环链表。不过，JDK1.8之后解决了这个问题，但是还是不建议在多线程下使用 `HashMap`，因为多线程下使用`HashMap`还是会存在其他问题比如数据丢失。并发环境下推荐`ConcurrentHashMap`。
<a name="POp82"></a>
## HashMap有哪几种常见的遍历方式？
`HashMap`遍历从大的方向来说，可分为以下4类：

1. 迭代器（Iterator）方式遍历；
2. For Each 方式遍历；
3. Lambda 表达式遍历（JDK1.8+）；
4. Streams API 遍历 （JDK1.8+）。

但是每种类型又有不同的实现方式，因此具体的遍历方式又可以分为以下7种：

1. 使用迭代器（Iterator）`EntrySet`方式遍历；
2. 使用迭代器（Iterator）`KeySet`方式遍历；
3. 使用 For Each `EntrySet`；
4. 使用 For Each `KeySet`；
5. 使用 Lambda 表达式进行遍历；
6. 使用 Stream API 单线程进行遍历；
7. 使用Stream API 多线程进行遍历。
<a name="X31Rm"></a>
### 1、迭代器 EntrySet
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        Iterator<Map.EntrySet<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hashNext()){
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="pJob8"></a>
### 2、迭代器 KeySet
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        Iterator<Integer, String> iterator = map.keySet().iterator();
        while (iterator.hashNext()){
            Integer key = iterator.next();
            System.out.println(key + " " + map.get(key));
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="o9SOO"></a>
### 3、ForEach EntrySet
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        for (Map.Entry<Integer, String> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="SjYQL"></a>
### 4、ForEach KeySet
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        for (Integer key : map.keySet()){
            System.out.println(key + " " + map.get(key));
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="m5jjo"></a>
### 5、Lambda
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        map.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="HHn7L"></a>
### 6、Stream API 单线程
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        map.entrySet().stream().forEach(entry) -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="BqRtL"></a>
### 7、Stream API多线程
```java
public class HashMapTset{
    public static void main(String[] args){
        // 创建并赋值HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");

        // 遍历
        map.entrySet().parallelStream().forEach(entry) -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
```
输出：
```
1 Java
2 JDK
3 Spring Framework
4 MyBatis framework
5 Java中文社群
```
<a name="bgnas"></a>
## ConcurrentHashMap和Hashtable的区别
`ConcurrentHashMap`和`Hashtable`的区别主要体现在实现线程安全的方式上不同。

- **底层数据结构：**JDK1.7的`ConcurrentHashMap`底层采用 **分段的数组加链表 **实现，JDK1.8采用的数据结构跟`HashMap 1.8`结构一样，数组 + 链表/红黑二叉树。`Hashtable`和JDK1.8之前的`HashMap`的底层数据结构类似吗，都是采用 **数组 + 链表 **的形式，数组是 `HashMap`的主题，链表则是为了解决哈希冲突而存在的。
- **实现线程安全的方式（重要！！！）：**
   - 在JDK1.7的时候，`ConcurrentHashMap`对整个桶数组进行了分割分段（`Segment`，分段锁），每一把锁只锁容器其中之一部分数据，多线程范文容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。
   - 到了JDK1.8的时候，`ConcurrentHashMap`已经摒弃了`Segment`的概念，而是直接用`Node`数组 + 链表 + 红黑树的数据结构来实现，并发控制使用`syncchronized`和`CAS`来操作。（JDK1.6之后`synchronized`锁做了很多优化）整个看起来就像是优化过且线程安全的`HashMap`，虽然在JDK1.8中还能看到`Segment`的数据结构，但是已经简化了属性，只是为了兼容旧版本。
   - `Hashtable`（同一把锁）：使用`synchronized`来保证线程安全，效率非常低下。当一个线程访问同步方法时，其他线程也访问同步方法，可能会进入阻塞或轮询状态，如使用put添加元素，另一个线程不能使用put添加元素，也不能使用get，竞争会越来越激烈效率越低。

JDK1.8中的`ConcurrentHashMap`不再是 **Segment数组 + HashEntry 数组 + 链表，**而是 **Node数组 + 链表 / 红黑树。**不过，Node只能用于链表的情况，红黑树的情况需要使用`TreeNode`。当冲突链表达到一定长度时，链表会转换为红黑树。<br />`TreeNode`是存储红黑树节点，被`TreeBin`包装。`TreeBin`通过`root`属性维护红黑树的根节点，因为红黑树在旋转的时候，根节点可能会被它原来的子节点替换掉，在这个时间点，如果有其他线程要写这颗红黑树就会发生线程不安全问题，所以在`ConcurrentHashMap`中`TreeBin`通过`waiter`属性维护当前使用这颗红黑树的线程，来防止其他线程的进入。
<a name="whsy5"></a>
#### JDK1.7和JDK1，8的ConcurrentHashMap实现有什么不同？

- **线程安全实现方式：**JDK1.7采用`Segment`分段锁来保证安全，`Segment`是继承自`ReentranLock`。JDK1.8放弃了`Segment`分段锁的设计，采用`Node + CAS + sychronized`保证线程安全，锁粒度更细，`synchronized`只锁定当前链表或红黑二叉树的首节点。
- **Hash碰撞解决方法：**JDK1.7采用拉链法，JDk1.8采用拉链法结合红黑树（链表长度超过一定阈值时，将链表转换为红黑树）。
- **并发度：**JDK1.7最大并发度是`Segment`的个数，默认是16。JDK1.8最大并发度是Node数组的大小，并发度更大。

