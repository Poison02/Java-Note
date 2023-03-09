<a name="bEEuO"></a>
# 集合概述
<a name="eZurm"></a>
## Java集合概览
Java集合，也叫做容器，主要是由两大接口派生而来；一个是`Collection`接口，主要用于存放单一元素；另一个是`Map`接口，主要用于存放键值对。对于`Collection`接口，下面又有三个主要的子接口：`List`、`Set`和`Queue`。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677307019081-b649be80-1753-4e27-829e-91676b747a6d.png#averageHue=%23181818&clientId=ueb44c1ea-3ccd-4&from=paste&height=613&id=uc7e70e87&name=image.png&originHeight=766&originWidth=925&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=97512&status=done&style=none&taskId=uf37510a5-2d24-4ce3-a850-c084fd865c1&title=&width=740)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677307036529-ff630cc8-4ae4-4a46-89e0-4c6ae9642825.png#averageHue=%23fdfcfc&clientId=ueb44c1ea-3ccd-4&from=paste&height=404&id=u3aa88537&name=image.png&originHeight=505&originWidth=946&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=71036&status=done&style=none&taskId=ufe706389-9405-41f3-9da9-0e112a58e05&title=&width=756.8)
<a name="eSMI0"></a>
### 说说List、Set、Queue、Map四者的区别？

- `List`（对付顺序的好帮手）：存储数据的元素是有序的、可重复的；
- `Set`（注重独一无二的性质）：存储的元素的无序的、不可重复的；
- `Queue`（实现排队功能的叫号机）：按特定的排队规则来确定先后顺序，存储的元素是有序的，可重复的。
- `Map`（用Key来搜索的专家）：使用键值对（key-value）存储，类似数学上的 y = (x)，x代表key，y代表value。key是无序的、不可重复的，value是无序的、可重复的，每个键最多映射到一个值。
<a name="NdlIP"></a>
### 集合框架底层数据结构总结
看一下`Collection`接口下面的集合。
<a name="KZGvB"></a>
#### List

- `ArrayList`：`Object[]`数组
- `Vector`：`Object[]`数组
- `LinkedList`：双向链表
<a name="drcXz"></a>
#### Set

- `HashSet`（无序，唯一）：基于`HashMap`实现的，底层采用`HashMap`来保存元素。
- `LinkedHashSet`：`LinkedHashSet`是`HashSet`的子类，并且通过其内部通过`LinkedhashMap`来实现的。有点类似于我们之前说的`LinkedHashMap`其内部是基于`HashMap`实现一样，不过还是有一点区别的。
- `TreeSet`（有序，唯一）：红黑树（自平衡的排序二叉树）。
<a name="KpCTG"></a>
#### Queue

- `PriorityQueue`：`Object[]`数组来实现二叉堆。
- `ArrayQueue`：`Object[]`数组 + 双指针。
<a name="dj415"></a>
#### Map

- `HashMap`：JDK1.8之前的`HashMap`是由数组 + 链表组成的，数组是`HashMap`的主体，链表则是主要的为了解决哈希冲突而存在的（”拉链法“解决冲突）。JDK1.8以后在解决哈希冲突时有了较大的改变，当链表长度大于阈值（默认是8）（将链表转换成红黑树前会判断，如果当前数组的长度小于64，那么会选择先进性数组扩容，而不是转换成红黑树）时，将链表转化成红黑树，以减少搜索时间。
- `LinkedHashMap`：`LinkedhashMap`继承自`HashMap`，所以它的底层依然是基于拉链式散列结构即由数组和红黑树组成。另外，`LinkedHashMap`在上面结构的基础上，增加了一条双向链表，是的上面的结构可以保持键值对的插入顺序。同时通过对链表进行相应的操作，实现了访问顺序相关逻辑。
- `Hashtable`：数组 + 链表组成的，数组是`Hashtable`的主体，链表则是为了解决哈希冲突而存在的。
- `TreeMap`：红黑树（自平衡的排序二叉树）
<a name="Fb5R1"></a>
### 如何选用集合？
主要根据集合的特点来选用，比如我们根据键值获取到元素值时就选用`Map`接口下的集合，需要排序时选择`TreeMap`，不需要排序时就选择`HashMap`，需要保证线程安全就选用`ConcurrenthashMap`。<br />当我们只需要存放元素值时，就选择实现`Collection`接口的集合，需要保证元素为一时选择实现`Set`接口的集合比如`TreeSet`或`HashSet`，不需要就选择实现`List`接口的比如`ArrayList`或者`LinkedList`，然后再根据实现这些接口的集合的特点来选用。
<a name="BLTat"></a>
### 为什么要使用集合？
当我们需要保存一组类型相同的数据的时候，我们应该使用一个容器来保存，这个容器就是数组，但是，使用数组存储对象具有一定的弊端，因为我们在实际开发中，存储的数据的类型是多种多样的，于是，就出现了集合，集合同样也是用来存储多个数据的。<br />数组的缺点是一旦声明之后，长度就不可变了；同时，声明数组时的数据类型也决定了该数组存储的数据的类型；而且，数组存储的数据有序的、可重复的，特点单一。但是集合提高了数据存储的灵活性，Java集合不仅可以用来存储不同类型不同数量的对象，还可以保存具有映射关系的数据。
<a name="G4vKF"></a>
### Collection子接口之List
<a name="Ifcpt"></a>
#### ArrayList和Vector的区别？

- `ArrayList`是`List`的主要实现类，底层使用`Object[]`存储，适用于频繁的查找工作，线程不安全；
- `Vector`是`List`的古老实现类，底层使用`Object[]`存储，线程安全的。
<a name="qvChf"></a>
#### ArrayList与LinkedList区别？

- **是否保证线程安全：**`ArrayList`和`LinkedList`都是不同步的，也就是不保证线程安全；
- **底层数据结构：**`ArrayList`底层使用的是`Object`数组；`LinkedList`底层使用的是**双向链表**
- **插入和删除是否受元素位置的影响：**
   - `ArrayList`采用数组存储，所以插入和删除元素的时间复杂度首元素位置的影响。比如：执行`add(E e)`方法的时候，`ArrayList`会默认在将指定的元素追加到此列表的末尾，这种情况时间复杂度就是O(1)。但是如果要在指定位置 i 插入和删除元素的话（`add(int index, E element)`）时间复杂度就为O(n - i)。因为在进行上述操作的时候集合中第 i 和第 i 个元素之后的 (n - 1) 个元素都要执行向后位 / 向前移一位的操作。
   - `LinkedList`采用链表存储，所以，如果是在头尾插入或者删除元素不受元素位置的影响（`add(E e)`、`addFirst(E e)`、`addLast(E e)`、`removeFirst()`、`removeLast()`），时间复杂度为 O(1)，如果是在指定位置 i 插入和删除元素的话，（`add(int index, E element)`），时间复杂度为 O(n)，因为需要先移动到指定位置再插入。
- **是否支持快速随机访问：**`LinkedList`不支持高效的随机元素访问，而`ArrayList`（实现了RandomAccess接口）支持。快速随机访问就是通过元素的序号快速获取元素对象（对应于`get(int index)`方法）。
- **内存空间占用：**`ArrayList`的空间浪费主要体现在 list 列表的结尾会预留一定的容量空间，而`LinkedList`的空间花费则体现在它的每一个元素都需要消耗比`ArrayList`更多的空间（因为要存放直接后继和直接前驱以及数据）。

ps：在项目中一般不会使用到`LinkedList`的，需要用到`LinkedList`的场景集合都可以使用`ArrayList`来代替，并且，性能通常会更好。
<a name="ldxlm"></a>
#### 补充内容：双向链表和双向循环链表
**双向链表：**包含两个指针，一个prev指向前一个节点，一个next指向后一个节点。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677312210908-7821ea0e-5b56-464d-a580-036310647813.png#averageHue=%231b1b1b&clientId=ueb44c1ea-3ccd-4&from=paste&height=190&id=ubb2425a7&name=image.png&originHeight=238&originWidth=912&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=37824&status=done&style=none&taskId=u0d3fc61c-d382-4901-a33d-82e49bf6074&title=&width=729.6)<br />**双向循环链表：**最后一个节点的 next 指向 head ，而 head 的 prev 指向最后一个节点，构成一个环。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677312419423-c1c6ea06-760e-4752-8081-c73232eb2a24.png#averageHue=%23181818&clientId=ueb44c1ea-3ccd-4&from=paste&height=258&id=ubcc1b109&name=image.png&originHeight=323&originWidth=827&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=38000&status=done&style=none&taskId=ud6bffe48-0b0d-4246-8b1b-f8b2efc0954&title=&width=661.6)
<a name="UQ5sx"></a>
#### 补充内容：RandomAccess接口
```java
public interface RandomAccess {
}
```
可以看到，这个接口中什么都没有，说明实现这个接口的类具有随机访问功能。<br />在`binarySearch()`方法中，他要判断传入的 list 是否`RandomAccess`的实例，如果是，调用`indexBinarySearch()`方法，如果不是，那么调用`iteratorBinarySearch()`方法。<br />`ArrayList`实现了`RandomAccess`接口，而`LinkedList`没有实现。为什么呢？我觉得还是和底层数据结构有关。`ArrayList`底层是数组，而`LinkedList`底层是链表。数组天然支持随机访问，时间复杂度为 O(1)，所以成为快速随机访问。链表需要遍历到特定位置才能访问特定位置的元素，时间复杂度为 O(n)，所以不支持快速访问。`ArrayList`实现了`RandomAccess`接口，就表明了它具有快速访问功能。`RandomAccess`接口只是标识，并不是说`ArrayList`实现`RandomAccess`接口才具有快速访问功能的。
<a name="sOodE"></a>
### Collection子接口之Set
<a name="FctQw"></a>
#### comparable和Comparator的区别

- `comparable`接口实际上是出自`java.lang`包，他有一个`comparaTo(Object obj)`方法用来排序。
- `comparator`接口实际上是出自`java.util`包，他有一个`compara(Object obj1, Object obj2)`方法用来排序。

一般我们需要对一个集合使用自定义排序时，我们就要重写`compareTo()`方法或`compare()`方法，当我们需要对某一个集合实现两种排序方式，比如一个song对象中的革命和歌手名分别采用一种排序方法的话，我们可以重写`compareTo()`方法和自制的`Comparator`方法或者以两个`Comparator`来实现歌名排序和歌星名排序，第二种代表我们只能使用两个参数版的`Collections.sort()`。
<a name="oGVyl"></a>
#### 无序性和不可重复性的含义是什么？

- 无序性不等于随机性，无序性是指存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值来决定的。
- 不可重复性是指添加的元素按照`equals()`判断时，返回`false`，需要同时重写`equals()`方法和`hashCode()`方法。
<a name="gggDl"></a>
#### 比较HashSet、LinkedHashSet、TreeSet三者的异同

- `HashSet`、`LinkedHashSet`和`TreeSet`都是`Set`接口的实现类，都能保证元素唯一，并且线程都是安全的。
- `HashSet`、`LinkedHashSet`和`TreeSet`的主要区别在于底层数据结构的不同。`HashSet`的底层数据结构是哈希表（基于`HashMap`实现的）。`LinkedHashSet`的底层数据结构是链表和哈希表，元素的插入和取出顺序满足 FIFO。`TreeSet`底层数据结构是红黑树，元素是有序的，排序的方式有自然排序和定制排序。
- 底层数据结构不同又导致这三者应用场景不同。`HashSet`用于不需要保证元素插入和取出顺序的场景，`LinkedHashSet`用于保证元素的的擦汗如和取出顺序满足FIFO的场景，`TreeSet`用于支持对元素自定义排序规则的场景。
<a name="FpFKP"></a>
### Collection子接口之Queue
<a name="ey6Hu"></a>
#### Queue与Deque的区别
`Queue`是单端队列，只能从一端插入元素，另一端删除元素，实现上一般遵循**先进先出（FIFO）**规则。<br />`Queue`扩展了`Collection`的接口，根据**因为容量问题而导致操作失败后处理方式的不同**可以分为两类方法：一种在操作失败后会抛出异常，另一种则会返回特殊值。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677376534769-b41ceb3b-d628-4b57-84f6-1e5f7ea73699.png#averageHue=%231f1f1f&clientId=u4e9c8a0a-f97e-4&from=paste&height=300&id=ue6657fce&name=image.png&originHeight=300&originWidth=483&originalType=binary&ratio=1&rotation=0&showTitle=false&size=30683&status=done&style=none&taskId=u195608bd-67d5-4d58-9891-9032e9b22f2&title=&width=483)<br />`Deque`是双端队列，在队列的两端均可以插入或者删除元素。<br />`Deque`扩展了`Queue`的接口，增加了在队首和队尾进行插入和删除的方法，同样根据失败了处理方式的不同分为两类：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1677376916661-6d901abf-f1e7-4623-83ab-72c5430214f4.png#averageHue=%231e1e1e&clientId=u4e9c8a0a-f97e-4&from=paste&height=640&id=ue52b7113&name=image.png&originHeight=640&originWidth=505&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69992&status=done&style=none&taskId=ucda93011-8e2f-4d71-847b-79fb393da26&title=&width=505)<br />事实上，`Deque`还提供有`push()`和`pop()`等其他方法，可用于模拟栈。
<a name="Y9Y2R"></a>
#### ArrayDeque与LinkedList的区别
`ArrayDeque`和`LinkedList`都实现了`Deque`接口，两者都具有队列的功能，但两者有什么区别？

- `ArrayDeque`是基于可变长的数组和双指针来实现，而`LinkedList`则通过链表来实现。
- `ArrayDeque`不支持存储`null`数据，而`LinkedList`支持。
- `ArrayDeque`是在JDK1.6才被引入，而`LinkedList`则早在JDK1.2就存在。
- `ArrayDeque`插入时可能存在扩容过程，不过均摊后的插入操作依然为O(1)。虽然`LinkedList`不需要扩容，但是每次插入数据时均需要申请新的堆空间，均摊性能相比较慢。

从性能的角度上，用`ArrayDeque`来实现队列要比`LinkedList`更好，此外，`ArrayDeque`也可以用于实现栈。
<a name="LyZPU"></a>
#### 说一说PriorityQueue
`PriorityQueue`是在JDK1.5中被引入的，其与`Queue`的区别在于元素出队顺序始于优先级相关的，即总是优先级最高的元素先出列。<br />列举一些要点：

- `PriorityQueue`利用了二叉堆的数据结构来实现的，底层使用可变长的数组来存储数据。
- `PriorityQueue`通过对元素的上浮和下沉，实现了在O(logn)的时间复杂度内插入元素和删除堆顶元素。
- `PriorityQueue`是非线程安全的，且不支持存储`null`和`non-comparable`对象。
- `PriorityQueue`默认是小顶堆，但可以接受一个`Comparator`作为构造参数，从而来自定义元素优先级的先后。

`PriorityQueue`在面试中更多出现在手撕算法，典型例题暴扣堆排序、求第K大的数、带权图的遍历等。
