<a name="uosaQ"></a>
# I/O
<a name="mZ3yK"></a>
## 何为I/O？
I/O（input/output）即输入和输出。<br />**先从计算机结构的角度解读一下I/O**<br />根据冯诺依曼结构，计算机结构分为五大部分：运算器，控制器，存储器，输入设备，输出设备。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678013040356-3a02b3dd-da4d-4bd6-aa64-87472211d792.png#averageHue=%23f4f4f4&clientId=u1947ea9a-a5af-4&from=paste&height=545&id=u46056bee&name=image.png&originHeight=545&originWidth=862&originalType=binary&ratio=1&rotation=0&showTitle=false&size=82053&status=done&style=none&taskId=u241afddf-d723-4b1a-a39f-bae2037c48c&title=&width=862)<br />输入设备（比如键盘）和输出设备（比如显示器）都属于外部设备。网卡、硬盘这种既可以属于输入设备，也可以属于输出设备。<br />输入设备向计算机输入数据，输出设备接收计算机输出的数据。<br />**从计算机结构的视角来看，I/O描述了计算机系统与外部设备之间通信的过程。**<br />**再从应用程序的角度解读I/O。**<br />根据操作系统的知识：为了保证操作系统的稳定性和安全性，一个进程的地址空间划分为**用户空间（User Space）**和 **内核空间（Kernel Space）。**<br />像我们平常运行的应用程序都是运行在用户空间，只有内核空间才能进行系统级别的资源有关的操作，比如文件管理、进程通信、内存管理等等。也就是说，我们想要进行I/O操作，一定是要依赖内核空间的能力。<br />并且，用户控件的程序不能直接访问内核空间。<br />当想要执行IO操作时，由于没有执行这些操作的权限，只能发起系统调用请求操作系统帮忙完成。<br />因此，用户进程想要执行IO操作的话，必须通过**系统调用**来间接访问内核空间。<br />我们在平常开发过程中接触最多的就是**磁盘IO（读写文件）**和 **网络IO（网络请求和响应）。**<br />**我们从应用程序的视角来看的话，我们的应用程序对操作系统的内核发起IO调用（系统调用），操作系统负责的内核执行具体的IO操作。也就是说，我们的应用程序实际上只是发起了IO操作的调用而已，具体IO的执行是由操作系统的内核完成的。**<br />当应用程序发起IO调用后，会经历两个步骤：

1. 内核等待I/O设备准备好数据
2. 内核将数据从内核空间拷贝到用户空间。
<a name="E3Les"></a>
## 有哪些常见的IO模型？
UNIX系统下，IO模型一共有5种：**同步阻塞I/O，同步非阻塞I/O，I/O多路复用，信号驱动I/O和异步I/O/**<br />这也是我们经常提到的5种IO模型。
<a name="WKTwP"></a>
# Java种3种常见的IO模型
<a name="y2ZpH"></a>
## BIO（Blocking I/O）
**同步阻塞IO模型**中，应用程序发起read调用后，会一直阻塞，知道内核把数据拷贝到用户空间。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678013839948-182cfc7b-252e-4793-8f83-f7b5abe333e0.png#averageHue=%23f0f0f0&clientId=u1947ea9a-a5af-4&from=paste&height=622&id=ued587a55&name=image.png&originHeight=622&originWidth=578&originalType=binary&ratio=1&rotation=0&showTitle=false&size=67176&status=done&style=none&taskId=u735f515b-0a29-4006-be42-ce87a40be2d&title=&width=578)<br />在客户端连接数量不高的情况下是没有问题的。但是，当面对十万甚至百万级别连接的时候，传统的BIO模型是无能为力的。因此，我们需要一种更高效的I/O处理模型来应对更高的并发量。
<a name="CEtlY"></a>
## NIO（Non-blocking/New I/O）
Java中的NIO于Java1.4中引入，对应`java.nio`包，提供了`Channel`，`Selector`，`Buffer`等抽象。NIO中的N可以理解为`Non-blocking`，不单纯是`New`。它是支持面向缓冲的，基于通道的I/O操作方法。对于高负载、高并发的（网络）应用，应使用NIO。<br />Java中的NIO可以看作**I/O多路复用模型。**也有很多人认为，Java中的NIO属于同步非阻塞IO模型。<br />我们先看看**同步非阻塞IO模型**。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678014094998-098aa7b9-6d20-49a9-8720-332a9654a513.png#averageHue=%23ebebea&clientId=u1947ea9a-a5af-4&from=paste&height=624&id=u4163cded&name=image.png&originHeight=624&originWidth=569&originalType=binary&ratio=1&rotation=0&showTitle=false&size=96342&status=done&style=none&taskId=uee80b8c9-645b-4410-9709-386a5dc1d02&title=&width=569)<br />同步非阻塞IO模型中，应用程序会一直发起`read`调用，等待数据从内核空间拷贝到用户空间的这段时间里，线程依然是阻塞的，直到在内核把数据拷贝到用户空间。<br />相比于同步阻塞IO模型，同步非阻塞IO模型确实有了很大改进。通过轮询操作，避免了一直阻塞。<br />但是，这种IO模型同样存在问题：**应用程序不断进行I/O系统调用轮询数据是否已经准备好的过程是十分小号CPU资源的。**<br />这个时候，**I/O多路复用模型**就上场了。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678014295625-c18bb217-52c9-4180-b6d9-6ed6138589c3.png#averageHue=%23eaeaea&clientId=u1947ea9a-a5af-4&from=paste&height=557&id=ufca400e0&name=image.png&originHeight=557&originWidth=563&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40797&status=done&style=none&taskId=u3daf4979-2a16-4935-a0b4-c3c3fe52be0&title=&width=563)<br />I/O多路复用模型中，线程首先发起`select`调用，询问内核数据是否准备就绪，等内核把数据准备好了，用户线程再发起`read`调用。`read`调用的过程（数据从内核空间 -> 用户空间）还是阻塞的。<br />目前支持IO多路复用的系统调用，有select， epoll等等。select系统调用，目前几乎在所有的操作系统上都支持。

- **select调用：**内核提供的系统调用，他支持一次查询多个系统调用的可用状态。极狐所有操作系统都支持。
- **epoll调用：**Linux2.6内核，属于select调用的增强版本，优化了IO的执行效率。

**IO多路复用模型，通过减少无效的系统调用，减少了对CPU资源的消耗。**<br />Java中的NIO，有一个非常重要的**选择器（selector）**的概念，也可以成为**多路复用器。**通过它，只需要一个线程便可以管理多个客户端连接。当客户端数据到了之后，才会为其服务。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678014746554-90612e9f-d4d0-423d-bc0f-2a553d49b5f7.png#averageHue=%23f2e6d4&clientId=u1947ea9a-a5af-4&from=paste&height=615&id=u1650be29&name=image.png&originHeight=615&originWidth=408&originalType=binary&ratio=1&rotation=0&showTitle=false&size=36286&status=done&style=none&taskId=u78b84816-39c0-4f1a-9ede-e7e4007f6b1&title=&width=408)
<a name="I5ARP"></a>
## AIO（Asynchronous I/O）
AIO也就是NIO2。Java7中引入了NIO的改进版NIO2，它是异步模型。<br />异步IO是基于事件和回调机制实现的。也就是应用操作之后会直接返回，不会堵塞在那里，当后台处理完成，操作系统会通知相应的线程进行后续的操作。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678014849052-1c07365f-54bf-4578-b79f-1c76c3b68d1f.png#averageHue=%23f1f1f1&clientId=u1947ea9a-a5af-4&from=paste&height=640&id=ub017f03f&name=image.png&originHeight=640&originWidth=564&originalType=binary&ratio=1&rotation=0&showTitle=false&size=61795&status=done&style=none&taskId=uc6e98858-6640-4ac1-9601-f8e085c89ed&title=&width=564)<br />目前来说AIO的应用还不是很广泛。`Netty`之前也尝试过使用AIO，不过放弃了。这是因为`Netty`使用AIO后，在Linux上性能并没有多少提升。
<a name="L6lSt"></a>
### 总结
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678014950707-6f2dd391-0cba-45a6-b730-3f36d2ec62f6.png#averageHue=%23f4f1f0&clientId=u1947ea9a-a5af-4&from=paste&height=687&id=ub2064955&name=image.png&originHeight=687&originWidth=714&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91770&status=done&style=none&taskId=u5b3b0492-a6eb-4aac-80dc-8927e40978a&title=&width=714)
