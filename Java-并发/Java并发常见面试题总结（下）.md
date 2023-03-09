<a name="wqnCC"></a>
# ThreadLocal
<a name="OIkAO"></a>
## ThreadLocal有什么用？
通常情况下，我们创建的变量是可以被任何一个线程访问并修改的。如果想实现每一个线程都有自己的专属贝蒂变量该如何解决呢？<br />JDK中自带的`ThreadLocal`类正式为了结局这样的问题。`ThreadLocal`**类主要解决的就是让每个线程绑定自己的值，可以将**`ThreadLocal`**类形象的比喻成存放数据的盒子，盒子中可以存储每个线程的私有数据。**<br />如果你创建了一个`ThreadLocal`变量，那么访问这个变量的每个线程都会有这个变量的本地副本，这也是`ThreadLocal`变量名的由来。他们可以使用`get()`和`set()`方法来获取默认值或将其值更改为当前线程所存的副本的值，从而避免了线程安全问题。
<a name="cr9Do"></a>
# 线程池
<a name="wNQiy"></a>
## 什么是线程池？
顾名思义，线程池就是管理一系列线程的资源池。当有任务要处理时，直接从线程池中获取来处理，处理完之后线程并不会直接被销毁，而是等待下一个任务。
<a name="XthtJ"></a>
## 为什么要用线程池？
池化技术想必已经屡见不鲜了，线程池、数据库连接池、Http连接池等等都是对这个思想的应用。池化技术的思想主要是为了减少每次获取资源的消耗，提高对资源的利用率。<br />**线程池**提供了一种限制和管理资源（包括执行一个任务）的方式。每个线程池还维护一些基本统计信息，例如已经完成任务的数量。<br />这里，《Java并发编程的艺术》中提到的**使用线程池的好处：**

- **降低资源消耗：**通过重重复利用已创建的线程降低线程创建和销毁造成的消耗。
- **提高响应速度：**当任务到达时，任务可以不需要等到线程创建就能立即执行。
- **提高线程的客观理性：**线程时稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
<a name="RGDt4"></a>
## 如何创建线程池？
<a name="Yv6XD"></a>
### 方式一：通过`ThreadPoolExecutor`构造函数来创建（推荐）
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678263064950-4e34ea34-03ac-4945-870f-345b5c7e3173.png#averageHue=%23ededce&clientId=u2464ce40-6abc-4&from=paste&height=123&id=uf4a7485f&name=image.png&originHeight=154&originWidth=827&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=137688&status=done&style=none&taskId=u48432e80-d9c2-465b-9c2e-2094376f03d&title=&width=661.6)
<a name="lSNyd"></a>
### 方式二：通过`Executor`框架的工具类`Executor`来创建。
我们可以创建多种类型的`ThreadPoolExecutor`：

- `FixedThreadPool`：该方法但会一个固定线程数量的线程池。该线程池中的线程数量始终不变。当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务。
- `SingleThreadExecutor`：该方法返回一个只有一个线程的线程池。若多于一个任务被提交到该线程池，任务会被保存在一个任务队列列中，待线程空闲，按先入先出的顺序执行队列中的任务。
- `CachedThreadPool`：该方法返回一个可根据实际情况调整线程数量的线程池。线程池的线程数量不确定，但若有空闲线程可以复用，则会优先使用可复用的线程。若所有线程均在工作，又有新的任务提交，则会创建新的线程处理任务。所有线程在当前任务执行完毕后，将返回线程池进行复用。
- `ScheduledThreadPool`：该方法返回一个用来在给定的延迟后运行任务或者定期执行任务的线程池。
<a name="VJg95"></a>
# 为什么不推荐使用内置线程池？
在《阿里巴巴Java开发手册》”并发处理“这一章节中，明确指出线程资源必须通过线程池提供，不允许在应用中自行显示创建线程。<br />为什么？<br />使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源开销，解决资源不足的问题/如果不使用线程池，有可能会造成系统创建大量同类线程而导致消耗完内存呢或者”过度切换“的问题。<br />另外，《阿里巴巴Java开发手册》中强制线程池不孕粗使用`Executor`去创建，而是通过`ThreadPoolExecutor`构造函数的方式，这样的处理方式能让写的同学更加明确线程池的运行规则，避免资源耗尽的风险。<br />`Executor`返回线程池对象的弊端如下：

- `FixedThreadPool`和`SingleThreadExecutor`：使用的是无界的`LinkedBlockQueue`，任务队列最大长度为`Integer.MAX_VALUE`，可能堆积大量的请求，从而导致OOM。
- `CachedThreadPool`：使用的是同步队列`SynchronousQueue`，允许创建的线程数量为`Integer.MAX_VALUE`，可能会创建大量线程，从而导致OOM。
- `ScheduledThreadPool`和`SingleThreadScheduledExecutor`：使用的无界的延迟阻塞队列`DelayedWorkQueue`，任务队列最大长度为`Integer.MAX_VALUE`，可能堆积大量的请求，从而导致OOM。
