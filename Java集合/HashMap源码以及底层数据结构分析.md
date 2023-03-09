<a name="f9nin"></a>
# HashMap简介
`HashMap`可以存储 null 的 key 和 value，但 null 作为键只能有一个，null 作为值可以有多个。<br />JKD1.8之前`HashMap`由 **数组+链表 **组成的，数组是`HashMap`的主体，链表则是主要为了解决哈希冲突而存在的（“拉链法”解决冲突）。JDK1.8以后的`HashMap`在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）（将链表转换成红黑树前会判断，如果当前数组的长度小于64，那么会选择先进行数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间。<br />`HashMap`默认的初始化大小为16.之后每次扩容，容量变为原来的2倍，并且，`HashMap`总是使用2的幂作为哈希表的大小。
<a name="ChRXF"></a>
# 底层数据结构分析
<a name="XWqMV"></a>
## JDK1.8之前
Jdk1.8之前`HashMap`底层是**数组+链表**结合在一起使用也就是**链表散列**。<br />`HashMap`通过`Key`的`hashCode`经过扰动函数处理过后得到`hash`值，然后通过`(n - 1) & hash`判断当前元素存放的位置（这里的n指的是数组的长度），如果当前位置存在元素的话，就判断该元素与要存入的元素的`hash`值以及`Key`是否相同，如果相同的话，直接覆盖，不相同就通过**拉链法**解决冲突。<br />所谓扰动函数指的就是`HashMap`的`hash`方法。使用`hash`方法也就是扰动函数是为了放置一些实现比较差的`hashCode()`方法，换句话说使用扰动函数之后可以减少碰撞。
<a name="skKBL"></a>
### JDK1.8HashMap的hash方法源码
JDK1.8的hash方法相比于JDK1.7hash方法更加简化，但是原理不变：
```java
static final int hash(Object key){
    int h;
	// key.hashCode() :返回散列值也就是hashCode
	// ^ 按位异或
    // >>> 无符号右移，忽略符号位，空格都以0补齐
	return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
对比一下JDK1.7的HashMap的hash方法源码：
```java
static int hash(int h){
    h ^= (h >>> 20) ^ (h >>> 12);
	return h ^ (h >>> 7) ^ (h >>> 4);
}
```
相比于JDK1.8的hash方法，JDK1.7的hash方法的性能会稍差一点点，因为毕竟扰动了4次。<br />所谓“**拉链法**”就是：将链表和数组相结合。也就是说创建一个链表数组，数组中每一个就是一个链表。若遇到哈希冲突，则将冲突的值加到链表中即可。
<a name="gcFiu"></a>
## JDK1.8之后
相比于之前的版本，JDk1.8以后在解决哈希冲突时有了较大的变化。<br />当链表长度大于阈值（默认为8时），会首先调用`treeifyBin()`方法。这个方法会根据`HashMap`数组来决定是否转换为红黑树。只有当数组长度大于或者等于64的情况下，才会执行转换红黑树操作，以减少搜索时间。否则，就是知识执行`resize()`方法对数组扩容。
<a name="NTtIr"></a>
# HashMap源码分析
`HashMap`中有四个构造方法，他们分别如下：
```java
// 默认构造函数。
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all   other fields defaulted
     }

     // 包含另一个“Map”的构造函数
     public HashMap(Map<? extends K, ? extends V> m) {
         this.loadFactor = DEFAULT_LOAD_FACTOR;
         putMapEntries(m, false);//下面会分析到这个方法
     }

     // 指定“容量大小”的构造函数
     public HashMap(int initialCapacity) {
         this(initialCapacity, DEFAULT_LOAD_FACTOR);
     }

     // 指定“容量大小”和“加载因子”的构造函数
     public HashMap(int initialCapacity, float loadFactor) {
         if (initialCapacity < 0)
             throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
         if (initialCapacity > MAXIMUM_CAPACITY)
             initialCapacity = MAXIMUM_CAPACITY;
         if (loadFactor <= 0 || Float.isNaN(loadFactor))
             throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
         this.loadFactor = loadFactor;
         this.threshold = tableSizeFor(initialCapacity);
     }
```
<a name="Metp0"></a>
## putMapExtries方法
```java
final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
    int s = m.size();
    if (s > 0) {
        // 判断table是否已经初始化
        if (table == null) { // pre-size
            // 未初始化，s为m的实际元素个数
            float ft = ((float)s / loadFactor) + 1.0F;
            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                    (int)ft : MAXIMUM_CAPACITY);
            // 计算得到的t大于阈值，则初始化阈值
            if (t > threshold)
                threshold = tableSizeFor(t);
        }
        // 已初始化，并且m元素个数大于阈值，进行扩容处理
        else if (s > threshold)
            resize();
        // 将m中的所有元素添加至HashMap中
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            putVal(hash(key), key, value, false, evict);
        }
    }
}
```
<a name="oyYdS"></a>
## put方法
`HashMap`只提供了`put`用于添加元素，`putVal`方法只是给`put`方法调用的一个方法，并没有提供给用户使用。<br />对`putVal`方法添加元素的分析如下：

1. 如果定位到的数组位置没有元素，就直接插入
2. 如果定位到的数组位置有元素就和要插入的`key`比较，如果`key`相同就直接覆盖，如果`key`不相同，就判断p是否是一个树节点，如果是就调用`e = ((TreeNode<K, v>)p).putTreeVal(this, tab, hash, key, value)`将元素添加进入。如果不是就遍历链表插入（插入的是链表尾部）。

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677847949442-9f12cb88-5a4f-4cfc-9aaa-c5f6733fa6c5.png#averageHue=%23f7f7f7&clientId=uea0f4a7c-4c9d-4&from=paste&height=591&id=uf58018d4&name=image.png&originHeight=591&originWidth=825&originalType=binary&ratio=1&rotation=0&showTitle=false&size=95350&status=done&style=none&taskId=u39fe4f2a-d837-400f-ba60-a2ace2be78e&title=&width=825)<br />说明：上图有两个小问题：

- 直接覆盖之后应该就会return，不会有后续操作。
- 当链表长度大于阈值（默认为8）并且`HashMap`数组长度唱过64的时候才会执行链表转红黑树的操作，否则就只是对数组扩容。
<a name="RRf8F"></a>
### 对比一下JDK1.7 put方法的代码：
分析：

1. 如果定位到的数组位置没有元素，就直接插入
2. 如果定位到的数组位置有元素，遍历以这个元素为头节点的链表，依次和插入的`key`比较，如果`key`相同就直接覆盖，不同就采用头插法插入元素。
```java
public V put(K key, V value)
    if (table == EMPTY_TABLE) {
    inflateTable(threshold);
}
    if (key == null)
        return putForNullKey(value);
    int hash = hash(key);
    int i = indexFor(hash, table.length);
    for (Entry<K,V> e = table[i]; e != null; e = e.next) { // 先遍历
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }

    modCount++;
    addEntry(hash, key, value, i);  // 再插入
    return null;
}
```
<a name="h2T4T"></a>
### get方法
```java
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        // 数组元素相等
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // 桶中不止一个节点
        if ((e = first.next) != null) {
            // 在树中get
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            // 在链表中get
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```
<a name="HCCFE"></a>
### resize方法
进行扩容，会伴随着一次重新`hash`分配，并且会遍历`hash`表中所有的元素，是非常耗时的。在编写程序中，尽量避免`resize`。
```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        // 超过最大值就不再扩充了，就只好随你碰撞去吧
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        // 没超过最大值，就扩充为原来的2倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {
        // signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    // 计算新的resize上限
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        // 把每个bucket都移动到新的buckets中
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else {
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        // 原索引
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        // 原索引+oldCap
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    // 原索引放到bucket里
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    // 原索引+oldCap放到bucket里
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```
