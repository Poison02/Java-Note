<a name="jpn24"></a>
# JMM（Java内存模型）
<a name="Ldfgb"></a>
# volatile关键字
<a name="q8AKK"></a>
## 如何保证变量的可见性？
在Java中，`volatile`关键字可以保证变量的可见性，如果我们将变量声明为`volatile`，这就指示`JMM`，这个变量是共享且不稳定的，每次使用它都到主存中进行读取。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678150476635-a7a8faad-f033-4411-8220-25490a66d80d.png#averageHue=%23191919&clientId=u8aad3533-e13d-4&from=paste&height=548&id=u2482ea6e&name=image.png&originHeight=685&originWidth=664&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=55203&status=done&style=none&taskId=ud1d24274-1557-4a33-9f42-fb429be7359&title=&width=531.2)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678150587438-21b0e471-6fbe-42d8-b1ef-0ba1f4c73d57.png#averageHue=%23181818&clientId=u8aad3533-e13d-4&from=paste&height=589&id=u0ba8a4e8&name=image.png&originHeight=736&originWidth=936&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=74019&status=done&style=none&taskId=u714fc7d7-9daa-48f1-ad3f-87b79bdab9c&title=&width=748.8)<br />`volatile`关键字其实并非是Java语言特有的，在C语言里也有，它最原始的意义就是金庸`CPU`缓存。如果我们将一个变量使用`volatile`修饰，这就指示编译器，这个变量是共享且不稳定的，每次使用它都到主存中进行读取。<br />`volatile`关键字能报则会那个数据的可见性，但不能保证数据的原子性。`synchronized`关键字两者都能保证。
<a name="pQXwn"></a>
## 如何禁止指令重排序？
在Java中，`volatile`关键字除了可以保证变量的可见性，还有一个重要的作用就是防止JVM的指令重排序。如果我们将变量声明为`volatile`，在对这个变量进行读写操作的时候，会通过插入特定的**内存屏障**的方式来禁止指令重排序。<br />在Java中，`Unsafe`类提供了三个开箱即用的内存屏障相关的方法，屏蔽了操作系统底层的差异。
```java
public native void loadFence();
public native void storeFence();
public native void fullFence();

```
理论上来说，通过这三种方法也可以实现和`volatile`进制重排序一样的效果，只是会麻烦一点。<br />**双重校验锁实现对象单例（线程安全）：**
```java
public class Singleton {

    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    public  static Singleton getUniqueInstance() {
       //先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (uniqueInstance == null) {
            //类对象加锁
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}

```
`uniqueInstance`采用`volatile`关键字修饰也是很有必要的，`uniqueInstance = new Singleton();`这段代码其实是为分为三步执行的。

1. 为`uniqueInstance`分配空间
2. 初始化`uniqueInstance`
3. 将`uniqueInstance`指向分配的内存地址

但是由于`JVM`具有指令重排的特性，执行顺序有可能变成1、3、2.指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的示例。例如，线程T1执行了1和3，此时T2调用`getUniqueInstance()`后发现`uniqueInstance`不为空，因此但会`uniqueInstance`，但此时`uniqueInstance`还未被初始化。
<a name="w54ZH"></a>
## `volatile`可以保证原子性么？
`volatile`关键字能保证变量的可见性，但不能保证对变量的操作是原子性的。<br />代码：
```java
public class VolatoleAtomicityDemo {
    public volatile static int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        VolatoleAtomicityDemo volatoleAtomicityDemo = new VolatoleAtomicityDemo();
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 500; j++) {
                    volatoleAtomicityDemo.increase();
                }
            });
        }
        // 等待1.5秒，保证上面程序执行完成
        Thread.sleep(1500);
        System.out.println(inc);
        threadPool.shutdown();
    }
}
```
正常情况下，这段代码输出：`2500`.但真正运行之后，每次输出结果都小于`2500`.<br />为什么呢？<br />很多人会误以为自增操作`inc++`是原子性的，实际上，`inc++`其实是一个符合操作，包括三步：

1. 读取inc的值
2. 对inc加1
3. 将inc的值写回内存

`volatile`是无法保证这三个操作是具有原子性的，有可能导致出现下面情况：

1. 线程1对`inc`进行读取之后，还未对其进行修改。线程2又读取了`inc`的值并对其进行修改（+1），再将`inc`的值写回内存。
2. 线程2操作完毕后，线程1对`inc`的值进行修改（+1），再将`inc`的值写回内存。

这也就导致两个线程分别对`inc`进行了一个自增操作后，`inc`实际上只增加了1。<br />让上面代码正确运行，只需要利用`synchronized`、`Lock`或`AtomicInteger`就可。<br />使用`synchronized`：
```java
public synchronized void increase() {
    inc++;
}

```
使用`AtomicInteger`：
```java
public AtomicInteger inc = new AtomicInteger();

public void increase() {
    inc.getAndIncrement();
}

```
使用`ReentranLock`：
```java
Lock lock = new ReentrantLock();
public void increase() {
    lock.lock();
    try {
        inc++;
    } finally {
        lock.unlock();
    }
}

```
<a name="KNouf"></a>
# 乐观锁和悲观锁
<a name="a283Z"></a>
## 什么是悲观锁？使用场景是什么？
悲观锁总是假设最坏的情况，认为共享资源每次被访问的时候就会出现问题（比如共享数据被修改），所以每次在获取资源操作的时候都会上锁，这样其他线程想拿到这个资源就会阻塞直到锁被上一个持有者释放。<br />也就是说，**共享资源每次只给一个线程使用，其他线程阻塞，用完后再把资源转让给其他线程。**<br />像Java中`synchronized`和`ReetrantLock`等独占锁就是悲观锁思想的实现。<br />**悲观锁通常多用于写多比较多的情况下（多写场景），避免频繁失败和充实影响性能。**
<a name="PUFm3"></a>
## 什么是乐观锁？使用场景是什么？
乐观锁总是假设最好的情况，认为共享资源每次被访问的时候不会出现问题，线程可以不停的去执行，无需加锁也无需等待，总是在提交修改的时候去验证对应的资源（也就是数据）是否被其他线程修改了。<br />在Java中`java.util.concurrent.atomic`包下面的原子变量类就是使用了乐观锁的一种实现方式CAS实现的。<br />**乐观锁通常用于写比较少的场景下（多读场景），避免频繁加锁影响性能，大大提升了系统的吞吐量。**
<a name="z7s3a"></a>
## 如何实现乐观锁？
乐观锁一般会使用版本号机制或CAS算法实现，CAS算法相对来说i更多一些。
<a name="ghJSy"></a>
### 版本号机制
一般是在数据表中加上一个数据版本号`version`字段，表示数据被修改的次数。当数据被修改时，`version`值会加一。当线程A要更新数据值时，在读取数据的同时也会读取`version`值，在提交更新时，若刚才读取到的`version`值为当前数据库中的`version`值相等时才会更新，否则重试更新操作，知道更新成功。
<a name="ACSYf"></a>
### CAS算法
CAS的全称是**Compare And Swap（比较和交换），**用于实现乐观锁，被广泛应用于各大框架中。CAS的思想很简单，就是用一个预期值和要更新的变量值进行比较，两只相等才会进行更新。<br />CAS是一个原子操作，底层依赖于一条CPU的原子指令。<br />原子操作即最小不可拆分的操作，也就是说操作一旦开始，就不能被打断，直到操作完成。<br />CAS涉及到三个操作数：

- **V：要更新的变量值（Var）**
- **E：预期值（Expected）**
- **N：拟写入的新值（New）**

当且仅当V的值等于E时，CAS通过原子方式用新值N来更新V的值。如果不等，说明已经有其他线程更新了V，则当前线程放弃更新。<br />Java语言并没有直接实现CAS，CAS相关的实现是通过C++内联汇编的形式实现的（JNI调用）。因此，CAS的具体实现和操作系统以及CPU都有关系。<br />`sun.misc`包下的`Unsafe`类提供了`compareAndSwapObject`、`compareAndSwapInt`、`compareAnsSwapLong`方法来实现的对`Object`、`int`、`long`类型的CAS操作。
```java
/**
	*  CAS
  * @param o         包含要修改field的对象
  * @param offset    对象中某field的偏移量
  * @param expected  期望值
  * @param update    更新值
  * @return          true | false
  */
public final native boolean compareAndSwapObject(Object o, long offset,  Object expected, Object update);

public final native boolean compareAndSwapInt(Object o, long offset, int expected,int update);

public final native boolean compareAndSwapLong(Object o, long offset, long expected, long update);

```
<a name="dcDqT"></a>
## 乐观锁存在哪些问题？
<a name="siplX"></a>
### ABA问题（最常见的问题）
如果一个变量V初次读取的时候是A值，并且在准备赋值的时候检查到它仍然是A值，那我们就能说明它的值没有被其他线程修改过了吗？显然不能，因为在这段时间它的值可能被改成其他值，然后又改回A，那CAS操作就会误以为它从来没被修改过。这个问题就叫做`ABA问题`。<br />ABA问题解决思路是在变量前面追加上**版本号或者时间戳。**JDK1.5后的`AtomicStampedReference`类就是用来解决ABA问题的。其中的`cpmpareAndSet()`方法就是首先检查当前引用是否等于预期引用，并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。
```java
public boolean compareAndSet(V   expectedReference,
                             V   newReference,
                             int expectedStamp,
                             int newStamp) {
    Pair<V> current = pair;
    return
        expectedReference == current.reference &&
        expectedStamp == current.stamp &&
        ((newReference == current.reference &&
          newStamp == current.stamp) ||
         casPair(current, Pair.of(newReference, newStamp)));
}

```
<a name="vr4xO"></a>
### 循环时间长开销大
CAS经常会用到自旋操作来进行重试，也就是不成功就一直循环执行知道成功。如果长时间不成功，会给CPU带来非常大的执行开销。<br />如果JVM能支持处理器提供的`pause`指令那么效率就会有一定的提升，`pause`指令有两个作用：

1. 可以延迟流水线执行指令，使CPU不会消耗过多的执行资源，延迟的事件取决于具体实现的版本，在一些处理器上延迟时间是0.
2. 可以避免在退出循环的时候因内存顺序冲突而引起CPU流水线被清空，从而提高CPU的执行效率。
<a name="MIEuj"></a>
### 只能保证一个共享变量的原子操作
CAS值对单个共享变量有效，当操作涉及跨多个共享变量时CAS无效。但是从JDK1.5开始，提供了`AtomicReference`类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来进行CAS操作。所以我们可以使用锁或者利用`AotmicReference`类把多个共享变量合并成一个共享变量来操作。
<a name="eZfIR"></a>
# synchronized关键字
<a name="mDnsE"></a>
## synchronized是什么？有什么用？
`synchronized`是Java中的一个关键字，翻译成中文是同步的意思，主要解决的是多个线程之间访问资源的同步性，可以保证被它修饰的方法或者代码在任意时刻只能有一个线程执行。<br />在Java早期版本中，`synchronized`属于**重量级锁，**效率低下。这是因为监视器锁（monitor）是依赖于底层的操作系统的`Mutex Lock`来实现的，Java的线程是映射到操作系统的原生线程之上的。如果要挂起或者唤醒一个线程，都需要操作系统帮忙完成，而操作系统实现线程之间的切换需要从用户态转换到内核态，这个状态之间的转换需要相对较长的时间，时间成本相对较高。<br />不过，在Java6之后，`synchronized`引入了大量的优化如自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量锁等技术来减少锁操作的开销，这些优化让`synchronized`锁的效率提升了很多。因此，`synchronized`还是可以在实际项目中使用的，像JDK源码、很多开源框架都大量使用了`synchronized`。
<a name="XNHCr"></a>
## 如何使用synchronized？
`synchronized`关键字的使用方式主要有下面3种：

1. 修饰实例方法
2. 修饰静态方法
3. 修饰代码块

1、**修饰实例方法**（锁当前对象实例）<br />给当前对象实例加锁，进入同步代码前要获得**当前对象实例的锁。**
```java
synchronized void method() {
    //业务代码
}

```
**2、修饰静态方法（锁当前类）**<br />给当前类加锁，会作用于类的所有对象实例，进入同步代码前要获得**当前class的锁。**<br />这是因为静态方法不属于任何一个实例对象，归整个类所有，不依赖于类的特定实例，被类的所有实例共享。
```java
synchronized static void method() {
    //业务代码
}

```
静态`synchronized`方法和非静态`synchronized`方法之间的调用互斥么？不互斥！如果一个线程A调用一个实例对象的非静态`synchronized`方法，而线程B需要调用这个实例对象所属类的静态`synchronized`方法，是允许的，不会发生互斥现象，因为访问静态`synchronized`方法占用的锁是当前类的锁，而访问非静态`synchronized`方法占用的锁是当前实例对象锁。<br />**3、修改代码块（锁指定对象/类）**<br />对括号里指定的对象/类加锁

- `synchronized(object)`：表示进入同步代码库前要获得**给定对象的锁。**
- `synchronized(类.class)`：表示进入同步代码前要获得**给定Class的锁。**
```java
synchronized(this) {
    //业务代码
}

```
总结：

- `synchronized`关键字加到`static`静态方法和`synchronized(class)`代码块上都是给Class类上锁。
- `synchronized`关键字加到实例方法上是给对象实例加锁。
- 尽量不要使用`synchronized(String a)`因为JVM中，字符串常量池具有缓存功能。
<a name="w0Uvy"></a>
## 构造方法可以用synchronized修饰么？
**构造方法不能使用synchronized关键字修饰。**<br />构造方法本身就属于线程安全的，不存在同步的构造方法一说。
<a name="l22Ja"></a>
## synchronized底层原理了解么？
`synchronized`关键字属于`JVM`层面的东西。<br />总结：

- synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。
- synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 ACC_SYNCHRONIZED 标识，该标识指明了该方法是一个同步方法。

**不过两者的本质都是对对象监视器 monitor 的获取。**

<a name="OWGf5"></a>
## JDK1.6之后的synchronized底层做了哪些优化？
JDK1.6 对锁的实现引入了大量的优化，如偏向锁、轻量级锁、自旋锁、适应性自旋锁、锁消除、锁粗化等技术来减少锁操作的开销。<br />锁主要存在四种状态：无锁状态、偏向锁状态、轻量级锁状态、重量级锁状态，他们会随着竞争的激烈而逐渐升级。注意锁可以升级不能降级，这种策略是为了提高获得锁和释放锁的效率。
<a name="IZViB"></a>
# synchronized和volatile的区别？
`synchronized`和`volatile`是两个互补的存在，而不是对立的存在。

- `volatile`关键字是线程同步的轻量级实现，所以`volatile`性能肯定比`synchronized`要好。但是`volatile`只能用于变量而`synchronized`可以修饰方法及代码块。
- `volatile`关键字能保证数据的可见性，但不能保证数据的原子性。`synchronized`两者能保证。
- `volatile`主要用于解决变量在多个线程之间的可见性，而`synchronized`解决的是多个线程之间访问资源的同步性。
<a name="N3iTp"></a>
# ReetrantLock
<a name="Wlx85"></a>
## ReetrantLock是什么？
`ReetrantLock`实现了`Lock`接口，是一个可重入且独占式的锁，和`synchronized`关键字类似。不过`ReetrantLock`更灵活，更强大，增加了轮询、超时、终端、公平锁和非公平锁等高级功能。
```java
public class ReentrantLock implements Lock, java.io.Serializable {}

```
`ReetrantLock`里面有一个内部类`Sync`，`Sync`继承AQS（`AbstractQueuedSynchronizer`），添加锁和释放锁的大部分操作实际上都是在`Sync`中实现的。`Sync`有公平锁`FairSync`和非公平锁`NoFairSync`两个字类。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678169472068-7c61da29-bd58-467e-8652-81a83c9fe530.png#averageHue=%232c323c&clientId=u3c038e6d-cfaf-4&from=paste&height=462&id=u9cd64bdc&name=image.png&originHeight=462&originWidth=760&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51335&status=done&style=none&taskId=u59ef8ef5-7ed4-4756-bad0-afa3e8822be&title=&width=760)<br />`ReetrantLock`默认使用非公平锁，也可以通过构造器来显示的使用公平锁。
```java
// 传入一个 boolean 值，true 时为公平锁，false 时为非公平锁
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}

```
从上面内容看，`ReetrantLock`的底层就是由AQS实现的。
<a name="Xm8v1"></a>
## 公平锁和非公平锁有什么区别？

- **公平锁：**锁被释放之后，先申请的线程先得到锁。性能较差一些，因为公平锁为了保证时间上的绝对顺序，上下文切换更频繁。
- **非公平锁：**锁被释放之后，后申请的线程可能会先获取到锁，是随机或者按照其他优先级排序的额。性能更好，但可能会导致某些线程永远无法获取到锁。
<a name="S5VCo"></a>
## synchronized和ReetrantLock的区别？
<a name="Xm8uU"></a>
### 两者都是可重入锁。
**可重入锁**也叫递归锁，指的是线程可以再次获取自己的内部锁。比如一个线程获得了某个对象的锁，此时这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候还是可以获取的，如果是不可重入锁的话，就会造成死锁。<br />JDK提供的所有现成的`Lock`实现类，包括`synchronized`关键字锁都是可重入的。
```java
public class ReentrantLockDemo {
    public synchronized void method1() {
        System.out.println("方法1");
        method2();
    }

    public synchronized void method2() {
        System.out.println("方法2");
    }
}

```
由于`synchronized`锁是可重入的，同一个线程在调用`method1()`时可以直接获得当前对象的锁，执行`method2()`的时候可以再次获取这个对象的锁，不会产生死锁问题。加入`synchronized`是不可重入锁的话，由于该对象的锁已经被当前线程所持有且无法释放，这就导致线程在执行`method2()`时获取锁失败，会出现死锁问题。
<a name="JfF9u"></a>
### synchronized依赖于JVM而ReetrantLock依赖于API
`synchronized`是依赖于JVM而实现的，前面我们讲到了 虚拟机团队在 JDK1.6 为 synchronized 关键字进行了很多优化，但是这些优化都是在虚拟机层面实现的，并没有直接暴露给我们。<br />`ReetrantLock`是JDK层面的，所以我们可以通过查看它的源代码来看是如何实现的。

<a name="vNmhR"></a>
### ReentrantLock 比 synchronized 增加了一些高级功能
主要有三点：

- **等待可中断：**`ReetrantLock`提供了一种能够中断等待锁的线程的机制，通过`lock.lockInterruotibly()`来实现这个机制。也就是说正在等待的线程可以选择放弃等待，改为处理其他事情。
- 可实现公平锁：`ReetrantLock`可以指定是公平锁还是非公平锁。而`synchronized`只能是非公平锁。所谓的公平锁就是先等待的线程先获得锁。`ReetrantLock`默认情况是非公平的，可以通过 ReentrantLock类的ReentrantLock(boolean fair)构造方法来制定是否是公平的。
- **可实现选择性通知（锁可以绑定多个条件）：**`synchronized`关键字与`wait()`和`notify()`/`notifyAll()`方法相结合可以实现等待/通知机制。`ReetrantLock`类当然也可以实现，但是需要借助于`Condition`接口与`newCondition()`方法。
<a name="W3Ehw"></a>
# 可中断锁和不可中断锁区别？

- **可中断锁：**获取锁的过程中可以被中断，不需要一直等到获取锁之后才能进行其他逻辑处理。`ReetrantLock`就属于是可中断锁。
- **不可中断锁：**一旦线程申请了锁，就只能等到拿到锁之后才能进行其他的逻辑处理。`synchronized`就属于是不可中断锁。
<a name="ljVZi"></a>
# ReentrantReadWriteLock
`ReentrantReadWriteLock`在实际项目中用的不多，JDK1.8中引入了性能更好的读写锁`StampedLock`。
<a name="uaUEl"></a>
## ReentrantReadWriteLock是什么？
`ReentrantReadWriteLock`实现了`ReadWriteLock`，是一个可重入的读写锁，既可以保证多个线程同时读的效率，同时又可以保证有写入操作时的线程安全。
```java
public class ReentrantReadWriteLock
        implements ReadWriteLock, java.io.Serializable{
}
public interface ReadWriteLock {
    Lock readLock();
    Lock writeLock();
}

```

- 一般锁进行并发控制的规则：读读互斥、读写互斥、写写斥斥。
- 读写锁进行并发控制的规则：读读不互斥、读写互斥、写写互斥（只有读读不互斥）。

`ReentrantReadWriteLock`其实是两把锁，一把是`WriteLock`（写锁），一把是`ReadLock`（读锁）。苏索是共享锁，些琐事独占锁。读锁可以被同时读，可以同时被多个线程持有，而写锁最多只能同时被一个线程持有。<br />和`ReentrantLock`一样，`ReentrantReadWriteLock`底层也是基于AQS实现的。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678170701457-637d43f2-e3f1-4cfe-b2a2-01dc8e4b435e.png#averageHue=%232d343d&clientId=u3c038e6d-cfaf-4&from=paste&height=308&id=u59463726&name=image.png&originHeight=308&originWidth=855&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69121&status=done&style=none&taskId=u07a203ab-8978-4ffd-81be-e5290e9e565&title=&width=855)<br />`ReentrantReadWriteLock`也支持公平锁和非公平锁，默认使用非公平锁，可以通过构造器来显示的指定。
```java
// 传入一个 boolean 值，true 时为公平锁，false 时为非公平锁
public ReentrantReadWriteLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
    readerLock = new ReadLock(this);
    writerLock = new WriteLock(this);
}

```
<a name="x3PC1"></a>
## ReentrantReadWriteLock适合什么场景？
由于`ReentrantReadWriteLock`既可以保证多个线程同时读的效率，同时又可以保证有写入操作时的线程安全。因此，在读多写少的情况下，使用`ReentrantReadWriteLock`能明显提升系统性能。
<a name="Mmdx8"></a>
## 共享锁和独占锁有什么区别？

- **共享锁：**一把锁可以被多个线程同时获得。
- **独占锁：**一把锁只能被一个线程获得。
<a name="V0U8x"></a>
## 线程持有读锁还能获取写锁吗？

- 在线程持有读锁的情况下，该线程不能取得写锁（因为获取写锁的时候，如果发现当前的读锁被占用，就马上获取失败，不管读锁是不是被当前线程持有）。
- 在线程持有写锁的情况下，该线程可以继续获取读锁（获取读锁时如果发现写锁被占用，只有写锁没有被当前线程占用的情况下才会获取失败）。
<a name="qhiOD"></a>
## 读锁为什么不能升级为写锁？
写锁可以降级为读锁，但是读锁不能升级为写锁。这是因为写锁降级为读锁会引起线程的争夺，毕竟写锁属于是独占锁，这样的话，会影响性能。<br />另外还可能会有死锁问题发生。举个例子：假设两个线程的读锁都想升级写锁，则需要双方都释放自己锁，而撒黄芳都不释放，就会产生死锁。
<a name="M0UHf"></a>
# StampedLock
<a name="tnAeO"></a>
## StampedLock是什么？
JDK1.8中引入的性能更好的读写锁，不可重入且不支持条件变量`Condition`。<br />不同于一般的`Lock`类，`StampedLock`并不是直接实现`Lock`或`ReadWriteLock`接口，而是基于**CLH锁**独立实现的。
```java
public class StampedLock implements java.io.Serializable {
}

```
`StampedLock`提供了三种模式的读写控制模式：读锁、写锁和乐观读。

- **写锁**：独占锁，一把锁只能被一个线程获得。当一个线程获取写锁后，其他请求读锁和写锁的线程必须等待。类似于 ReentrantReadWriteLock 的写锁，不过这里的写锁是不可重入的。
- **读锁** （悲观读）：共享锁，没有线程获取写锁的情况下，多个线程可以同时持有读锁。如果己经有线程持有写锁，则其他线程请求获取该读锁会被阻塞。类似于 ReentrantReadWriteLock 的读锁，不过这里的读锁是不可重入的。
- **乐观读** ：允许多个线程获取乐观读以及读锁。同时允许一个写线程获取写锁。

`StampedLock`还支持这三种所在一定条件下进行相互转换：
```java
long tryConvertToWriteLock(long stamp){}
long tryConvertToReadLock(long stamp){}
long tryConvertToOptimisticRead(long stamp){}

```
`StampedLock`在获取锁的时候会返回一个long类型的数据戳，该数据用于稍后的锁释放参数，如果返回的数据戳为0则表示锁获取失败。当前线程持有了锁再次获取锁还是会返回一个新的数据戳，这也是`StampedLock`不可重入的原因;
```java
// 写锁
public long writeLock() {
    long s, next;  // bypass acquireWrite in fully unlocked case only
    return ((((s = state) & ABITS) == 0L &&
             U.compareAndSwapLong(this, STATE, s, next = s + WBIT)) ?
            next : acquireWrite(false, 0L));
}
// 读锁
public long readLock() {
    long s = state, next;  // bypass acquireRead on common uncontended case
    return ((whead == wtail && (s & ABITS) < RFULL &&
             U.compareAndSwapLong(this, STATE, s, next = s + RUNIT)) ?
            next : acquireRead(false, 0L));
}
// 乐观读
public long tryOptimisticRead() {
    long s;
    return (((s = state) & WBIT) == 0L) ? (s & SBITS) : 0L;
}

```
<a name="BX8Iu"></a>
## StampedLock的性能为什么更好？
 相比于传统读写锁多出来的乐观读是StampedLock比 ReadWriteLock 性能更好的关键原因。StampedLock 的乐观读允许一个写线程获取写锁，所以不会导致所有写线程阻塞，也就是当读多写少的时候，写线程有机会获取写锁，减少了线程饥饿的问题，吞吐量大大提高。
<a name="gEDWN"></a>
## StampedLock适合什么场景？
和 ReentrantReadWriteLock 一样，StampedLock 同样适合读多写少的业务场景，可以作为 ReentrantReadWriteLock的替代品，性能更好。<br />不过，需要注意的是StampedLock不可重入，不支持条件变量 Conditon，对中断操作支持也不友好（使用不当容易导致 CPU 飙升）。如果你需要用到 ReentrantLock 的一些高级性能，就不太建议使用 StampedLock 了。<br />另外，StampedLock 性能虽好，但使用起来相对比较麻烦，一旦使用不当，就会出现生产问题。
<a name="EsXGu"></a>
## StampedLock的底层原理了解？
`StampedLock`不是直接实现`Lock`或`ReadWriteLock`接口，而是基于**CLH锁**独立实现的。CLH锁是对自旋锁的一种改良，是一种隐式的链表队列。`StampedLock`通过CLH队列进行线程的管理，通过同步状态值`state`来表示锁的状态和类型。
