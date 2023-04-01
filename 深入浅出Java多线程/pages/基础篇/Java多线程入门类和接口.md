<a name="L1qKm"></a>
# 2.1 Thread类和Runnable接口
上一章我们了解了操作系统中多线程的基本概念。那么在Java中，我们是如何使用多线程的呢？<br />首先，我们需要有一个线程类。JDK提供了 `Thread`类和`Runnable`接口让我们实现自己的线程类。

- 继承`Thread`类，并重写`run`方法。
- 实现`Runnable`接口的`run`方法。
<a name="ysFNy"></a>
## 2.1.1 继承Thread类
首先我们看看怎么用`Thread`和`Runnable`来写一个Java多线程程序。<br />首先是继承`Thread`类：
```java
/**
 * @author Zch
 **/
public class ThreadDemo {

    public static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread.run");
        }
    }

    public static void main(String[] args) {
        Thread myThread = new MyThread();

        myThread.start();
    }
}
```
主要必须要调用`start()`方法后，该线程才算启动！
> 我们在程序中调用了start()方法后，虚拟机会先为我们创建一个线程，然后等到这个线程第一次得到时间片时再调用run()方法。
> 注意不可多次调用start()方法。在第一次调用start()方法后，再次调用start()方法会抛出IllegalThreadStateException异常。

<a name="geQhD"></a>
## 2.1.2 实现Runnable接口
接下来我们看一下`Runnable`接口：
```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```
我们可以使用**Java8的函数式编程，**示例代码：
```java
/**
 * @author Zch
 **/
public class RunnableDemo{

    public static class MyThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable.run");
        }
    }

    public static void main(String[] args) {

        new Thread(new MyThread()).start();

        // Java8函数式编程，Lambda表达式
        new Thread(() -> {
            System.out.println("Java8匿名内部类");
        }).start();
    }
}
```
<a name="Z1FG4"></a>
## 2.1.3 Thread类构造方法
`Thread`类是一个`Runnable`接口的实现类，我们可以看看`Thread`类的源码。<br />查看`Thread`类的构造方法，发现其实是一个简单调用一个私有的`init`方法来实现初始化。`init`的方法签名：
```java
// Thread类源码 

// 片段1 - init方法
private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc,
                      boolean inheritThreadLocals)

// 片段2 - 构造函数调用init方法
public Thread(Runnable target) {
    init(null, target, "Thread-" + nextThreadNum(), 0);
}

// 片段3 - 使用在init方法里初始化AccessControlContext类型的私有属性
this.inheritedAccessControlContext = 
    acc != null ? acc : AccessController.getContext();

// 片段4 - 两个对用于支持ThreadLocal的私有属性
ThreadLocal.ThreadLocalMap threadLocals = null;
ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
```
解释`init`方法的这些参数：

- `g`：线程组，指定这个线程是在哪个线程组下；
- `target`：指定要执行的任务；
- `name`：线程的名字，多个线程的名字是可以重复的。如果不指定名字，片段2中使用`"Thread-" + nextThreadNum()`表示；
- `acc`：见片段3，用于初始化私有变量`inheritedAccessControlContext`。
> 这个变量有点神奇。它是一个私有变量，但是在`Thread`类里只有`init`方法对它机械性初始化，在`exit`方法把它设为null。其他没有任何地方使用它。

- `inheritThreadLocals`：可继承的`ThreadLocal`，见片段4，`Thread`类里面有两个私有属性来支持`ThreadLocal`。

实际情况下，我们大多是直接调用下面两个构造方法：
```java
Thread(Runnable target);
Thread(Runnable target, String name);
```
<a name="RLm4r"></a>
## 2.1.4 Thread类的几个常用方法
这里介绍一下`Thread`类的几个常用的方法：

- `currentThread()`：静态方法，返回对当前正在执行的线程对象的引用；
- `start()`：开始执行线程的方法，Java虚拟机会调用线程内的`run()`方法
- `yield()`：yield在英语中有放弃的意思，同样，这里的`yield()`指的是当前线程愿意让出对当前处理器的占用。这里需要注意的是，就算当前线程调用了`yield()`方法，程序在调度的时候，也还有可能继续运行这个线程的。
- `sleep()`：静态方法，使当前线程睡眠一段时间
- `join()`：使当前线程等待另一个线程执行完毕之后再继续执行，内部调用的是Object类的wait方法实现的。
<a name="TWbHv"></a>
## 2.1.4 Thread类与Runnable接口的比较：
实现一个自定义的线程类，可以有继承`Thread`类或者实现`Runnable`接口这两种方式，他们之间有什么优劣你？

- 由于”Java单继承，多实现“的特性，Runnable接口使用起来比Thread更灵活
- Runnable接口出现更符合面向对象，将线程单独进行对象的封装
- Runnable接口出现，降低了线程对象和线程任务的耦合性
- 如果使用线程时不需要使用Thread类的诸多方法，显然使用Runnable接口更为轻量

所以，我们通常优先使用“实现Runnable接口”这种方式来自定义线程类
<a name="x7t4g"></a>
# 2.2 Callable、Future与FutureTask
通常来说，使用`Runnable`和`Thread`来创建一个新的线程。但是他们有一个弊端，就是`run`方法是没有返回值的。而有些时候我们希望开启一个线程执行一个任务，并且这个任务执行完成后有一个返回值。<br />JKD提供了`Callable`接口与`Future`接口为我们解决这个问题，这就是所谓的“异步”模型。
<a name="VgZfc"></a>
## 2.2.1 Callable接口
`Callable`与`Runnable`类似，同样是只有一个抽象方法的函数式接口。不同的是，`Callable`提供的方法是**有返回值**的，而且支持**泛型。**
```java
@FunctionalInterface
public interface Callable<V> {
	V call() throws Exception;
}
```
那一般是怎么使用`Callable`的呢？`Callable`一般是配合线程池工具`ExecutorService`来使用的。我们会在后续章节解释线程池的使用。这里只介绍`ExecutorService`可以使用`submit`方法来让一个`Callable`接口执行。它会返回一个`Future`，我们后续的程序可以通过这个`Future`的`get`方法得到结果。<br />这里看一个简单的demo：
```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Zch
 **/
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // 模拟计算需要1s
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws Exception{
        // 使用
        ExecutorService executor = Executors.newCachedThreadPool();
        CallableDemo demo = new CallableDemo();
        Future<Integer> result = executor.submit(demo);
        // 注意调用get方法会阻塞当前线程，知道得到结果
        // 所以实际编码中建议使用可以设置超时时间的重载get方法
        System.out.println(result.get());
    }
}

```
<a name="WN9tV"></a>
## 2.2.2 Future接口
`Future`接口只有几个比较简单的方法
```java
public abstract interface Future<V> {
    public abstract boolean cancel(boolean paramBoolean);
    public abstract boolean isCancelled();
    public abstract boolean isDone();
    public abstract V get() throws InterruptedException, ExecutionException;
    public abstract V get(long paramLong, TimeUnit paramTimeUnit)
            throws InterruptedException, ExecutionException, TimeoutException;
}
```
`cancel`方法是视图取消一个线程的执行。<br />注意是**试图取消，**并不一定能取消成功。因为任务可能已完成、已取消、或者一些其他因素不能取消，存在取消失败的可能。`boolean`类型的返回值是“是否取消成功”的意思，参数`paramBoolean`表示是否采用中断的方式取消线程执行。<br />所以有时候，为了让任务有能够取消的功能，就使用`Callable`来代替`Runnable`。如果为了可取消行而使用`Future`但又不提供可用的结果，则可以声明`Future<?>`形式类型、并返回`null`作为底层任务的结果。
<a name="RiyiC"></a>
## 2.2.3 FutureTask类
上面介绍了`Future`接口。这个接口有一个实现类叫`FutureTask`。`FutureTask`是实现的`RUnnableFuture`接口的，而`RunnableFuture`接口同时继承了`Runnable`和`Future`接口。
```java
public interface RunnableFuture<V> extends Runnable, Future<V> {
    /**
     * Sets this Future to the result of its computation
     * unless it has been cancelled.
     */
    void run();
}
```
那`FutureTask`类有什么用呢？为什么要有一个`FutureTask`类？上面说到了`Future`只是一个借口，而他俩面的`cancel`、`get`、`isDone`等方法要自己实现起来都是非常复杂的。所以JDK提供了一个`FutureTask`类来供我们使用。<br />示例：
```java
import java.util.concurrent.*;

/**
 * @author Zch
 **/
public class CallableDemo implements Callable<Integer> {

    public static void main(String[] args) throws Exception{
        // 下面是使用FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executor.submit(futureTask);
        System.out.println(futureTask.get());
    }
}

class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 3;
    }
}

```
使用上和第一个Demo有点区别，首先，调用`submit`方法是没有返回值的。这里实际上是调用的`submit(Runnable task)`方法，而上面的Demo，调用的是`submit(Callable<T> task)`方法。<br />然后，这里是使用`FutureTask`直接取`get`取值，而上面的Demo是通过`submit`方法返回的`Future`去取值。<br />在很多高并发的环境下，有可能`Callable`和`FutureTask`会创建多次。`FutureTask`能够在高并发环境下**确保任务只执行一次。**
<a name="nLFut"></a>
## 2.2.4 FutureTask的几个状态
```java
/**
  *
  * state可能的状态转变路径如下：
  * NEW -> COMPLETING -> NORMAL
  * NEW -> COMPLETING -> EXCEPTIONAL
  * NEW -> CANCELLED
  * NEW -> INTERRUPTING -> INTERRUPTED
  */
private volatile int state;
private static final int NEW          = 0;
private static final int COMPLETING   = 1;
private static final int NORMAL       = 2;
private static final int EXCEPTIONAL  = 3;
private static final int CANCELLED    = 4;
private static final int INTERRUPTING = 5;
private static final int INTERRUPTED  = 6;
```
> state表示任务的运行状态，初始状态为NEW，运行状态只会在set、setException、cancel方法中终止。COMPLETING、INTERRUPTING是任务完成后的瞬时状态。

