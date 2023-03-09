<a name="phWSd"></a>
# 什么是线程和进程？
<a name="JIW3c"></a>
## 何为进程？
进程是程序的一次创建过程，是系统运行程序的基本单位，因此进程是动态的。系统运行一个程序即是一个进程从创建，运行到消亡的过程。<br />在Java中，当我们启动`main`函数时其实就是启动了一个`JVM`的进程，而`main`函数所在的线程就是这个进程中的一个线程，也称主线程。<br />如下图，在`Windows`中通过查看任务管理器的方式，我们可以清楚看到`Windows`当前运行的进程（`.exe`文件的运行）。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678072889383-ab4e8b0d-ff0c-42b1-a238-19bee8c2a5e3.png#averageHue=%23f9f4e5&clientId=u3e0a2bca-2eed-4&from=paste&height=594&id=u134bd441&name=image.png&originHeight=742&originWidth=834&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=75827&status=done&style=none&taskId=u593bb761-e471-457e-96dc-cc1b7d9c143&title=&width=667.2)
<a name="JfGq5"></a>
## 何为线程？
线程与进程相似，但线程是一个比进程更小的执行单位。一个进程在其执行的过程中可以产生多个线程。与进程不同的是同类的多个线程共享进程的**堆**和**方法区**资源，但每个线程有自己的**程序计数器、虚拟机栈和本地方法栈，**所以系统在产生一个线程，或是在各个线程之间作切换工作时，负担要比进程小得多，也正因为如此，线程被称为轻量级进程。<br />Java程序天生就是多线程程序，我们可以通过`JMX`来看看一个普通的Java程序有哪些线程，
```java
public class MultiThread {
	public static void main(String[] args) {
		// 获取 Java 线程管理 MXBean
	ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		// 遍历线程信息，仅打印线程 ID 和线程名称信息
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
		}
	}
}

```
输出：
```java
[6] Monitor Ctrl-Break
[5] Attach Listener // 添加事件
[4] Signal Dispatcher // 分发处理给JVM信号的线程
[3] Finalizer // 调用对象finalize方法的线程
[2] Reference Handler //清楚reference线程
[1] main // 程序入口
```
从上面可以看出：**一个Java程序的运行是main线程和多个其他线程同时进行。**
<a name="sfAPH"></a>
# 请简要描述线程与进程的关系，区别和优缺点？
从`JVM`角度说明进程和线程之间的关系。
<a name="HMfZj"></a>
## 图解进程和线程的关系
图：Java内存区域图<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678073813838-82b8c0bb-63af-4fe4-9082-661c768e9455.png#averageHue=%231c1c1c&clientId=u3e0a2bca-2eed-4&from=paste&height=614&id=u2e23b2f7&name=image.png&originHeight=768&originWidth=709&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=88250&status=done&style=none&taskId=u09184bfd-9808-4ede-a0ad-76f9d08e906&title=&width=567.2)<br />从上图可看出：一个进程中可以有多个线程，多个线程共享像进程的**堆**和**方法区（JDK1.8之后的元空间）资源，但是每个进程有自己的程序计数器、虚拟机栈和本地方法栈。**<br />**总结：线程是进程划分成的更小的运行单位。线程和进程最大的不同在于基本上各进程是独立的，而各线程则不一定，因为同一进程中的线程极有可能会相互影响。线程执行开销小，但不利于资源的管理和保护；而今晨正相反。**
<a name="TRsuF"></a>
## 程序计数器为什么是私有的？
程序计数器主要有下面两个作用：

1. 字节码解释器通过改变程序计数器来一次读取指令，从而实现代码的流程控制，如：顺序执行、选择、循环、异常处理。
2. 在多线程的情况下，程序计数器用于记录当前线程执行的位置，从而当线程被切换回来的时候能够知道该线程上次运行到那个地方。

需要注意：如果执行的是`native`方法，那么程序计数器记录的是`undefined`地址，只有执行的是Java代码时程序计数器记录的才是吓一跳指令的地址。<br />所以，程序计数器私有主要是为了**县茨城切换后能恢复到正确的执行位置。**
<a name="aGoTR"></a>
## 虚拟机栈和本地方法栈为什么是私有的？

- **虚拟机栈：**每个Java方法在执行的同时会创建一个栈帧用于存储局部变量表、操作数栈、常量池引用等信息。从方法调用直至执行完成的过程，就对应着一个栈帧在Java虚拟机栈中入栈和出栈的过程。
- **本地方法栈：**和虚拟机栈所发挥的作用非常相似，区别是：**虚拟机栈为虚拟机执行Java方法（也就是字节码）服务，而本地方法栈则为虚拟机使用到的Native服务。**在HotSpot虚拟机中和Java虚拟机栈合二为一。

所以，为了**保证线程中的局部变量不被别的线程访问到，**虚拟机栈和本地基站是线程私有的。
<a name="Lisua"></a>
## 简单了解堆和方法区
堆和方法区是所有线程共享的资源，其中堆是进程中最大的一块内存，主要用于存放新创建的对象（极狐所有对象都在这里分配内存），方法区主要用于存放已被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。
<a name="lpHkN"></a>
# 并发与并行的区别

- **并发：**两个及两个以上的作业在**同一时间段**内执行。
- **并行：**两个及两个以上的作业在同一**时刻**执行。

最关键的点是：是否在**同时**执行。
<a name="xuGRE"></a>
# 同步和异步的区别

- **同步：**发出一个调用之后，在没有得到结果之前，该调用就不可以反悔，一直等待。
- **异步：**调用在发出之后，不用等待返回结果，该调用直接返回。
<a name="LRdTk"></a>
# 为什么要使用多线程？
先从总体上来说：

- **从计算机底层来说：**线程可以比作是轻量级的进程，是程序执行的最小单位，线程间的切换和调度的成本远远小于进程。另外，多核CPU时代意味着多个贤臣可以同时运行，这减少了线程上下文切换的开销。
- **从当代互连网的发展趋势来说：**现在的系统动不动就要求几百万级甚至千万级的并发量，而多线长城并发编程正是开发高并发系统的基础，利用好多线程机制可以大大提高系统整体的并发能力以及性能。

再深入到计算机底层来说：

- **单核时代：**在单核时代多线程主要是为了提高单线程利用`CPU`和`IO`系统的效率。加入如只运行了一个Java进程的情况，当我们请求`IO`的时候，如果Java进程中只有一个线程，此线程被`IO`阻塞则整个进程被阻塞。`CPU`和`IO`设备只有一个在运行，那么可以简单地说系统整体效率只有50%。当使用多线程的时候，一个线程被`IO`阻塞，其他线程还可以继续使用`CPU`。从而提高了Java进程利用系统资源的整体效率。
- **多核时代：**多核时代多线程主要是为了提高进程利用多核`CPU`的能力。举个例子，假如我们要计算一个复杂的任务，我们只用一个线程的话，不论系统有几个`CPU`核心，都只会有一个`CPU`核心被利用到。而创建多个线程，这些线程可以被映射到底层多个`CPU`上执行，在任务中的多个线程没有资源竞争的情况下，任务执行的效率会有显著性的提高，约等于（单核时执行时间/CPU核心数）。
<a name="mpqWA"></a>
# 使用多线程可能带来什么问题？
并发编程的目的就是为了能提高程序的执行效率提高程序运行速度，但是并发编程并不总是能提高程序运行速度的，并且并发编程可能会遇到很多问题，比如：内存泄露、死锁、线程不安全等等。
<a name="Cbvvp"></a>
# 说说线程的生命周期和状态？
Java线程在运行的生命周期中指定时刻只可能处于下面6中不同状态的其中一个状态：

- `NEW`：初始状态，线程被创建出来但没有被调用`start()`；
- `RUNNABLE`：运行状态，线程被调用了`start()`等待运行的状态；
- `BLOCKED`：阻塞状态，需要等待锁释放；
- `WAITING`：等待状态，表示该线程需要等待其他线程做出一些特定动作（通知或中断）；
- `TIME_WAITING`：超时等待状态，可以在指定的时间后自行返回而不是像`WAITING`那样一直等待；
- `TREMINATED`：终止状态，表示该线程已经运行完毕。

线程在生命周期中并不是固定处于某一个状态而是随着代码的执行在不同状态之间切换。<br />Java线程状态变迁图：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678081235877-7f52341d-e006-4675-960d-f82eb1fad192.png#averageHue=%23f7f7f7&clientId=ubcdbd225-a282-4&from=paste&height=684&id=u41bdebf6&name=image.png&originHeight=684&originWidth=866&originalType=binary&ratio=1&rotation=0&showTitle=false&size=171919&status=done&style=none&taskId=u3c70c009-fa3b-40af-9bec-1d4797d907d&title=&width=866)<br />从上图可以看出：线程创建之后他将处于`NEW（新建）`状态，调用`start()`方法后开始运行，线程这时候处于`READY（可运行）`状态。可运行状态的线程获得了`CPU`时间片（timeslice）后就处于`RUNNING（运行）`状态。<br />在操作系统层面，线程有`READY`和`RUNNING`状态；而在JVM层面，只能看到`RUNNING`状态，所以Java系统一般将这两个状态统称为`RUNNABLE（运行中）`状态。<br />为什么JVM没有区分这两种状态？现在的时分（time-sharing）多任务（multi-task）操作系统架构通常都是用所谓的：时间分片（time quantum or time slice）方式进行抢占式（preemptive）轮转调度（round-robin式）。这个时间分片通常是很小的，一个线程一次最多只能在`CPU`上运行比如10-20ms的事件（此时处于`RUNNING`状态），也就是大概只有0.01s这一量级，时间分片用后就要被切换下来放入调度队列的末尾等待再次调度。（也就是回到`READY`状态）。线程切换的如此之快，区分这两种状态就没什么意义了）。

- 当线程执行`wait()`方法之后，线程进入`WAITING`状态。进入等待状态的线程需要依靠其他线程的通知才能够返回到运行状态。
- `TIMED_WAITING（超时等待）`状态相当于在等待状态的基础上增加了超时限制，比如通过`sleep(long millis)`方法或`wait(long millis)`方法可以将线程置于`TIMED_WAITING`状态。当超时事件结束后，线程将会返回到`RUNNABLE`状态。
- 当线程进入`synchronized`方法/块或者调用`wait`后（被`notify`）重新进入`synchronized`方法/块，但是锁被其他线程占有，这个时候线程就会进入`BLOCKED（阻塞）`状态。
- 线程在执行完了`run()`方法之后就会进入到`TERMINATED（终止）`状态。
<a name="fmPb1"></a>
# 什么是上下文切换？
线程在执行过程中会有自己的运行条件和状态（也称上下文），比如上文所说到过的程序计数器，栈信息等。当出现如下情况的时候，线程会从`CPU`状态中退出。

- 主动让出`CPU`，比如调用了`sleep()`、`wait()`等
- 时间片用完，因为操作系统要防止要给线程或者进程长时间占用`CPU`导致其他线程或者进程饿死。
- 调用了阻塞类型的系统终端，比如请求IO，线程被阻塞。
- 被终止或结束运行。

这其中前三种都会发生线程切换，线程切换意味着需要保存当前线程的上下文，留待线程下次占用`CPU`的时候恢复现场。并加载下一个将要占用`CPU`的线程上下文。这就是所谓的**上下文切换。**<br />上下文切换是现代操作系统的基本功能，因其每次需要保存信息恢复信息，这将会占用`CPU`，内存等资源进行处理，也就意味着效率会有一定损耗，如果频繁切换就会造成整体效率低下。
<a name="JVSMq"></a>
# 什么是线程死锁？如何避免死锁？
<a name="yfMQo"></a>
## 线程死锁：
线程死锁描述的是这样一种情况：多个线程同时被阻塞，他们中的一个或者全部都在等待某个资源被释放。由于线程被无限期的阻塞，因此程序不可能正常终止。<br />如下图，线程A持有资源2，线程B持有资源1，他们同时都想申请对方的资源，所以这两个线程就会互相等待而进入死锁状态。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678083676247-7d6e3816-3bd3-404c-8ebe-ad057f1bc6b0.png#averageHue=%23e8ebef&clientId=ubcdbd225-a282-4&from=paste&height=219&id=u76a1dc4f&name=image.png&originHeight=219&originWidth=346&originalType=binary&ratio=1&rotation=0&showTitle=false&size=15665&status=done&style=none&taskId=u5281de86-8230-49dd-8093-cf35dc34c5c&title=&width=346)

代码示例：
```java
public class DeadLockDemo {
    private static Object resource1 = new Object();//资源 1
    private static Object resource2 = new Object();//资源 2

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程 2").start();
    }
}

```
输出：
```java
Thread[线程 1,5,main]get resource1
Thread[线程 2,5,main]get resource2
Thread[线程 1,5,main]waiting get resource2
Thread[线程 2,5,main]waiting get resource1

```
线程A通过`synchronized(resource1)`获得`resource1`的监视器锁，然后通过`Thread.sleep(1000)`让线程A休眠1s为的是让线程B得到执行然后获取到`resource2`的监视器锁。线程A和线程B休眠结束了都开始企图请求获取对方的资源，然后这两个线程就会陷入互相等待的状态，这也就产生了死锁。
<a name="OMXUT"></a>
## 产生死锁的四个必要条件。

1. 互斥条件：该资源任意一个时刻只由一个线程占用；
2. 请求和保持条件：一个线程因请求资源而阻塞时，对己获得的资源保持不放；
3. 不剥夺条件：线程以获得的资源在未使用完之前不能被其他线程强行剥夺，只有自己使用完毕后才释放资源；
4. 循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系。
<a name="hNAEf"></a>
## 如何预防和避免线程死锁？
<a name="KbxHm"></a>
### 如何预防死锁？
破坏死锁的产生的必要条件即可。

1. **破坏请求与保持条件：**一次性申请所有的资源。
2. **破坏不剥夺条件：**占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放他占有的资源。
3. **破坏循环等待条件：**靠按序申请资源来预防。按某一顺序申请资源，释放资源则反序释放。破坏循环等待条件。
<a name="sUUrF"></a>
### 如何避免死锁？
避免死锁就是在资源分配时，借助于算法（比如银行家算法）对资源分配进行计算评估，使其进入安全状态。<br />**安全状态**指的是系统能够按照某种线程推进顺序（P1，P2，P3...Pn）来为每个线程分配所需资源，直到满足每个线程对资源的最大需求，使每个线程都可顺利完成。称`<P1,P2,P3...Pn>`序列为安全序列。<br />我们对上面线程2的代码修改一下就不会产生死锁了
```java
new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 2").start();

```
改了之后输出：
```java
Thread[线程 1,5,main]get resource1
Thread[线程 1,5,main]waiting get resource2
Thread[线程 1,5,main]get resource2
Thread[线程 2,5,main]get resource1
Thread[线程 2,5,main]waiting get resource2
Thread[线程 2,5,main]get resource2

Process finished with exit code 0

```
分析：<br />线程1首先获得到`resource1`的监视器锁，这时候线程2就获取不到了。然后线程1再去获取`resource2`的监视器锁，可以获取到。然后线程1释放了对`resource1`、`resource2`的监视器锁的占用，线程2获取到就可以执行了。这样就破坏了破坏循环等待条件，因此避免了死锁。
<a name="VKL0t"></a>
# sleep()方法和wait()方法对比
共同点：两者都可以暂停线程的执行。<br />区别：

- `sleep()`方法没有释放锁，而`wait()`方法释放了锁。
- `wait()`通常被用于线程间交互/通信，`sleep()`通常被用于暂停执行。
- `wait()`方法被调用后，线程不会自动苏醒，需要别的线程调用同一个对象上的`notify()`或者`notifyAll()`方法。`sleep()`方法执行完后，线程就会自动苏醒，或者也可以使用`wait(long timeout)`超时后贤臣会自动苏醒。
- `sleep()`是`Thread`类的静态本地方法，`wait()`则是`Object`类的本地方法。
<a name="jQsDF"></a>
## 为什么wait()方法不定义在Thread中？
`wait()`是让获取对象锁的线程实现等待，会自动释放当前贤臣占有的对象锁。每个对象（`Object`）都拥有对象锁，既然要释放当前线程占有的对象锁并让其进入`WAITING`状态，自然是要操作对应的兑现（`Object`）而非当前的线程（`Thread`）。<br />类似的问题：为什么`sleep()`方法定义在`Thread`中？<br />因为`sleep()`是让当前线程暂停执行，不涉及到对象类，也不需要获得对象锁。
<a name="Zwbkk"></a>
# 可以直接调用Thread类的run方法吗？
这是一个非常经典的Java多线程面试问题，而且在面试中会经常被问到。<br />new一个`Thread`，线程进入了新建状态。调用`start()`方法，会启动一个线程并使线程进入了一个就绪状态，当分配到时间片后就可以开始运行了。`start()`会执行线程的相应准备工作，然后自动执行`run()`方法的内容，这是真正的多线程工作。但是，直接执行了`run()`方法，会把`run()`方法当成一个main线程下的普通方法去执行，并不会在某个线程中执行它，所以这并不是多线程工作。
<a name="vvmMA"></a>
# 总结
调用`start()`方法方可启动线程并使线程进入就绪状态，直接执行`run()`方法的话不会以多线程的方式执行。
