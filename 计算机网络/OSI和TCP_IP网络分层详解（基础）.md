<a name="eVz3W"></a>
# OSI七层模型
OSI七层模型 是国际标准化组织提出一个网络分层模型，其大体结构以及每一层提供的功能如下图：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678264501500-deeba383-0a0c-40d7-a2a3-9ae7086c967a.png#averageHue=%23f3f1f0&clientId=u5d3e4706-ddb8-4&from=paste&height=422&id=u3b80fe04&name=image.png&originHeight=528&originWidth=795&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=150161&status=done&style=none&taskId=u654e9aa7-0ecb-4401-8cb7-9df995f868f&title=&width=636)<br />每一层都专注做一件事情，并且每一层都需要使用下一层提供的功能比如传输层需要使用网络层提供的路由和寻址功能，这样穿传输层才知道把数据传输到那里去。<br />OSI的七层体系结构概念清楚，理论完善，但是他比较复杂而且不实用，且有些功能在多个层、层中重复出现。<br />下面这张图是网上找的：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678264644261-05c84326-2f01-41a6-88ce-fcb5176f9329.png#averageHue=%23e1e5aa&clientId=u5d3e4706-ddb8-4&from=paste&height=502&id=uadf5aeb3&name=image.png&originHeight=627&originWidth=833&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=362724&status=done&style=none&taskId=uf4708c96-361e-46f5-a5f3-09713a17a51&title=&width=666.4)<br />为什么OSI七层模型这么俩还，但是却干不过TCP/IP四层模型呢？

1. OSI的专家缺乏实际经验，他们在玩长城OSI标准时缺乏商业驱动力
2. OSI的协议实现起来过分复杂，而且运行效率很低
3. OSI指定标准的周期太长，因而使得按OSI标准生产的设备无法及时进入市场
4. OSI的层次划分不太合理，有些功能在多个从层次中重复出现。

OSI七层模型总结图片<br />![osi-model-detail.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678265037682-5e9ae036-6d16-4227-8920-33c7318bc786.png#averageHue=%238ba956&clientId=u5d3e4706-ddb8-4&from=paste&height=1270&id=ucd57c5a5&name=osi-model-detail.png&originHeight=1588&originWidth=1120&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=153920&status=done&style=none&taskId=u3f370a0e-bfbf-4c45-9bc0-7f4b24101d9&title=&width=896)
<a name="G1zUG"></a>
# TCP/IP四层模型
TCP/IP四层模型是目前被广泛采用的一种模型，我们可以将TCP/IP模型看做是OSI七层模型的精简版本，有以下四层组成：

1. 应用层
2. 传输层
3. 网络层
4. 网络接口层

需要注意的是，OSI只能和TCP/IP简单对应起来：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678265340992-19ff8237-6986-429c-8a9f-3cff27bfaf9c.png#averageHue=%23f5f3f3&clientId=u5d3e4706-ddb8-4&from=paste&height=346&id=ufbdf080f&name=image.png&originHeight=433&originWidth=845&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=120281&status=done&style=none&taskId=ub88f8b2d-54df-47e9-ab22-4d5588982ed&title=&width=676)
<a name="pWZEI"></a>
# 应用层（Application layer）
**应用层位于传输层之上，主要提供两个终端设备上的应用程序之间信息交换的服务，它定义了信息交换的格式，消息会交给下一层传输层来传输**。我们把应用层交互的数据单元称为报文。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678265916944-b937f51e-c7e6-4103-ac9b-87dcb49000ba.png#averageHue=%23dfdfde&clientId=u5d3e4706-ddb8-4&from=paste&height=376&id=u18c9e2b3&name=image.png&originHeight=470&originWidth=649&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=98308&status=done&style=none&taskId=ub0f1be25-6a4c-46c6-accf-33cdc12bc32&title=&width=519.2)<br />应用层协议定义了网络通信规则，遂于不同的网络应用需要不同的应用层协议。在互联网中应用层协议很多，如支持Web应用的HTTP协议，支持电子邮件的SMTP协议等等。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678266133690-ad3adf1a-694c-44e9-9b16-523b107d2222.png#averageHue=%23d4e2f5&clientId=u5d3e4706-ddb8-4&from=paste&height=338&id=uf658ce4b&name=image.png&originHeight=422&originWidth=604&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=56224&status=done&style=none&taskId=ud1ee512f-9880-42e2-b759-c6cb555e6b8&title=&width=483.2)
<a name="ZA3Qo"></a>
# 传输层（Transport layer）
**传输层的主要任务就是负责向两台终端设备进程之间的通信提供通用的数据传输服务。**应用进程利用该服务传送应用层报文。”通用的“是指并不针对某一个特定的网络应用，而是多种应用可以使用同一个运输层服务。<br />**运输层主要使用一下两种协议：**

1. **传输控制协议TCP**（Transmission Control Protocol）提供**面向连接**的，**可靠的**数据传输服务。
2. **用户数据协议UDP**（User Datagram Protocol）提供**无连接**的，尽最大努力的数据传输服务（不保证数据传输的可靠性）。
<a name="WsgR9"></a>
# 网络层（Network layer）
**网络层负责为分组交换网上的不同主机提供通信服务。**在发送数据时，网络层把运输层产生的报文段或用户数据报封装成分组和包进行传送。在`TCP/IP`体系结构中，由于网络层使用IP协议，因此分组也叫`IP数据报`，简称数据报。<br />注意：**不要把运输层的”用户数据报UDP“和网络层的”IP数据报“弄混。**<br />**网络层的还有一个任务就是选择合适的路由，使源主机运输层所传下来的分组，能通过网络层中的路由器找到目的主机。**<br />网络层中的”网络“二字不是我们经常谈到的具体网络，而是指计算机网络体系结构中第三层的名称。<br />互连网是有大量的异构网络通过路由器相互连接起来的。互联网使用的网络层协议是无连接的网际协议（Internet Protocol）和许多路由选择协议，因此互联网的网络层也叫做**网际层**或**IP层。**<br />**网络层常见协议：**

- **IP：网际协议：**网际协议`IP`是`TCP/IP`协议中最重要的协议之一，也是网络层最重要的协议之一，`IP`协议的作用包括寻址规约、定义数据包的格式等等，是网络层信息传输的主力协议。目前IP协议主要分为两种，一种是过去的`IPV4`，另一种是比较新的`IPV6`.目前这两种协议都在是哟个，但后者已经被提出取代前者。
- **ARP协议：**`ARP协议`，全称地址解析协议（Address Resolutin Protocol），他解决的是网络层地址和链路层地址之间的转换问题。因为一个`IP数据报`在物理层上传输的过程中，总是需要知道下一跳（物理上的下一个目的地）该去往何处，但`IP地址`属于逻辑地址，而`MAC地址`才是物理地址，`ARP协议`解决了`IP地址`转`MAC地址`的一些问题。
- **NAT：网络地址转换协议：**`NAT协议`（Network Address Translation）的应用场景如同它的名称-网络地址抓换，应用于内部网到外部网的地址转换过程中。具体地说，在一个小的子网（局域网，`LAN`）内，各主机使用的是同一个`LAN`下的`IP地址`，但在该`LAN`以外，在广域网（`WAN`）中，需要一个统一的`IP地址`来标识该`LAN`在整个`Internet`上的位置。
<a name="Tg16R"></a>
# 网络接口层（Network internet layer）
我们可以把网络接口层看措施数据链路层和物理层的合体。

1. 数据链路层（data link layer）通常简称为链路层（两台主机之间的数据传输，总是在一段一段的链路上传送的）。**数据链路层的作用是将网络层交下来的IP数据报组装成帧，在两个相邻接点的链路上传送帧。每一帧包括数据和必要的控制信息（如同步信息，地址信息，差错控制等）。**
2. **物理层的作用是实现相邻计算机节点之间比特流的透明传送，尽可能屏蔽掉具体传输介质和物理设备的差异**

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678283013215-f870def2-de66-4d75-b54f-b1a925584e2b.png#averageHue=%23d9e7fa&clientId=ub19f1d8a-6672-4&from=paste&height=298&id=u7162755d&name=image.png&originHeight=298&originWidth=465&originalType=binary&ratio=1&rotation=0&showTitle=false&size=25576&status=done&style=none&taskId=u92954e34-1958-4f50-9ac0-495e9bedee5&title=&width=465)
<a name="jDRS9"></a>
# 总结
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1678283028388-97d8ca2b-8b88-4f3f-a4c3-2a43e5e13249.png#averageHue=%23faf9f9&clientId=ub19f1d8a-6672-4&from=paste&height=447&id=u27f03f32&name=image.png&originHeight=447&originWidth=871&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94199&status=done&style=none&taskId=u59c134bb-672b-4d13-b1a5-5291adc7858&title=&width=871)
<a name="ncRfw"></a>
## 应用层协议

- `HTTP协议`（超文本传输协议，网页浏览器常用的协议）
- `DHCP协议`（动态主机配置）
- `DNS系统原理`（域名系统）
- `FTP协议`（文件传输协议）
- `Telnet协议`（远程登陆协议）
- `电子邮件协议`（SMTP、POP3、IMAP）
- ...
<a name="W5yRL"></a>
## 传输层协议

- `TCP协议`
   - 报文段结构
   - 可靠数据传输
   - 流量控制
   - 拥塞控制
- `UDP协议`
   - 报文段结构
   - `RDT`（可靠数据传输协议）
   <a name="nJUnN"></a>
## 网络层协议

- `IP协议`（TCP/IP协议的基础，分为IPV4和IPV6）
- `ARP协议`（地址解析协议，用于解析IP地址和MAC地址之间的映射）
- `ICMP协议`（控制报文协议，用于发送控制消息）
- `NAT协议`（网络地址转换协议）
- `RIP协议、OSPF协议、BGP协议（路由选择协议）`
- ...
<a name="o6GQp"></a>
## 网络接口层

- 差错检测技术
- 多路访问协议（信道复用技术）
- `CSMA/CD协议`
- `MAC协议`
- 以太网技术
- ...
<a name="U64TW"></a>
# 网络分层的原因
说到分层，我们在用框架进行后台开发时，往往会按照每一层做不同的事情的原则将系统分为三层：

- `Repository`（数据库操作）
- `Service`（业务操作）
- `Controller`（前后端数据交互）

复杂的系统需要分层，因为每一层都需要专注于一类事情。网络分层也一样，每一层只准主语做一类事情。<br />为什么网络需要分层？

1. **各层之间相互独立：**各层之间相互独立，各层之间不需要关心其他层是如何实现的，只需要知道自己如何调好下层提供好的功能就可以了（可以简单理解为接口调用）。这个和我们对开发时系统进行分层是一个道理。
2. **提高了整体灵活性：**每一层都可以使用最适合的技术来实现，你只需要保证你提供的功能以及暴露的接口的规则没有改变就行了。这个和我们平时开发系统的时候要求的**高内聚、低耦合**的原则也是可以对应上的。
3. **大问题化小：**分层可以将复杂的网络问题分解成许多比较小的、界限比较清晰简单的小问题来处理和解决。这样使得复杂的计算机网络系统变得已与设计，实现和标准化。这个和我们平时开发的时候，一般会将系统功能分解，然后将复杂的问题分解为更小的问题是相对应的，这些较小的问题具有更好的编辑（目标和接口）定义。

**计算机科学领域的任何问题都可以通过增加一个间接的中间层来解决，计算机整个体系从上到下都是按照严格的层次结构设计的。**
