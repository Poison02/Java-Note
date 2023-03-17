<a name="uvYc2"></a>
# Java基础
<a name="NuyDS"></a>
## 1、static静态变量
`static`修饰的变量我们称之为**静态变量，**没有用`static`修饰的变量称之为**实例变量，**他们的区别是：

- **静态变量**是随着类加载时被完成初始化的，它在内存中仅有一个，且JVM也只会为它分配一次内存，同时类所有的实例都共享静态变量，可以直接通过**类名访问它。**
- **实例变量**不同，它是随着实例的，每创建一个实例就会产生一个产生实例变量，它与该实例同生死。

一般在这两种情况下使用静态变量：对象之间共享数据、访问方便。“static”关键字表明一个成员变量或者是成员方法可以在没有所属的类的实例变量的情况下被访问。<br />**static属性**其本身是一个公共的属性，其可以有类名称直接调用。<br />结论：在以后进行类设计的时候首选的一定是`非static`属性（95%），而考虑到公共信息存储时才会使用`static`属性（5%），非static属性必须在实例化对象产生之后才可以使用，二static属性可以在没有实例化对象产生的情况下直接通过类名称进行调用。<br />**static可以修饰属性，方法和代码块**

1. static修饰属性（类变量），那么这个属性就可以用`类名.属性名`来访问，也就是使这个属性称为本类的类变量，为本类对象所共有。这个属性就是全类公有。（公有的类变量与对象无关，只和类有关）。
2. static修饰方法（静态方法），会使这个方法称为整个类所公有的方法，可以用`类名.方法名`调用。
3. static修饰初始代码块，这时这个初始代码块就叫做静态初始代码块，这个代码块只在类加载时被执行一次。可以用静态初始代码块初始化这个类。动态初始代码块，写在类体中的“{}”，这个代码块是在生成对象的初始化属性时运行。这种代码块叫做动态初始代码块。

static只能修饰内部类。正常的外部类不能使用static修饰。
<a name="OsX3j"></a>
## 2、JDK，JRE和JVM的联系区别

- `JDK`提供编译、调试和运行功能，用于开发，包含了`JRE`和`JVM`
- `JRE`提供了运行Java程序的平台，包含了`JVM`
- `JVM`是Java的虚拟机，提供了内存管理、垃圾回收和安全机制等。
<a name="YpUYo"></a>
## 3、堆和栈的区别，类和对象：
**堆内存：**保存的是对象的具体信息，在程序之中堆内存空间的开辟是通过new完成的。<br />**栈内存：**保存的是一块堆内存的地址，即：通过地址找到堆内存，而后找到对象内容。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679050954480-db9c4f8a-6df3-4e65-922c-2b55ac5995d3.png#averageHue=%23f0d8c1&clientId=u77046ff1-f550-4&from=paste&height=206&id=ue4591956&name=image.png&originHeight=258&originWidth=710&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=95262&status=done&style=none&taskId=u72f6ff1a-6ea2-4b1b-9d3a-468cd5b10c3&title=&width=568)<br />引用传递：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679050968208-d0591e0a-d2e6-4f81-b5b7-f0cc30e29158.png#averageHue=%23f1ede3&clientId=u77046ff1-f550-4&from=paste&height=250&id=u0a481c45&name=image.png&originHeight=313&originWidth=719&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=112539&status=done&style=none&taskId=ud375b240-9275-497b-9eeb-067322a0f61&title=&width=575.2)<br />**类**是对某一类事物的共性的抽象概念。<br />**对象**描述的是一个具体的产物。<br />类是一个模板，而对象才是类可以使用的实例，先有类再有对象。<br />对象必须实例化以后才能使用，`Person person = new Person()`
<a name="tKt2A"></a>
## 4、垃圾空间
所谓的**垃圾空间**指的就是没有任何栈内存所指向的堆内存空间，所有的垃圾将被`GC（Garbage Collector、垃圾收集器）`定期进行回收并且释放无用内存，但是如果垃圾过多，一定会影响到GC的处理性能，从而降低整体的程序性能，那么在实际的开发之中，对于垃圾的产生应该越少越好。
<a name="Iqe5D"></a>
## 5、请解释String比较中“==”和“equals()”区别？

- **==：**如果是基本数据类型，进行的是数值比较，如果用于引用数据类型，比较的则是两个内存的地址数值。
- **equals()**：是类所提供的一个比较方法，可以直接进行字符串内容的判断，**区分大小写.**
```java
// 正确
String str = "sss";
System.out.println(str.equals(null)); // false
// 错误
String str = null;
System.out.println(str.equals("sss")); // 空指针异常
```
```java
// 直接赋值过程中，内存池数据自动保存，
// 如果有相同数据定义时可以减少对象的产生
String strA = "mldn";
String strB = "mldn";
System.out.println(strA == strB); //true
```
判断字符串是否为空的方法：
```java
if (s == null || s.equals(""));
if (s == null || s.length == 0);
if (s == null || s.isEmpty());
if (s == null || s == "");
// null代表没有地址，声明了一个空对象，根本不是字符串
// "" 是字符串，有地址，只是内容为空
```
是否区分大小写判断：
```java
//区分大小写
String strA = "mldn";
String strB = "MLDN";
System.out.println(strA.equals(strB));//false
//不区分大小写
String strA = "mldn";
String strB = "MLDN";
System.out.println(strA.equalsIgnoreCase(strB));//true

```
String对象两种对象实例化方式：
```java
// 直接赋值，只会产生一个实例化对象，
// 并且可以自动保存到对象池中，以实现该字符串实例的重用；
String strA = "mldn";
// 构造方法，会产生两个实例化对象，并且不会自动入池，
// 无法实现对象的重用，但是可以利用intern()方法手工入池处理。
String strB = new String("mldn");
System.out.println(strA == strB);//false

```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679051644042-118b8ba6-ca10-4324-97ee-3b781015cc72.png#averageHue=%23f0d8c2&clientId=u77046ff1-f550-4&from=paste&height=233&id=uac34a749&name=image.png&originHeight=291&originWidth=764&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=89516&status=done&style=none&taskId=ufa51b842-e5fc-45ce-b5eb-908d663ae73&title=&width=611.2)<br />下面这种字符串修改方式会带来大量的垃圾回收（不推荐）：
```java
String str = "www";
str += "baidu";
str = str + "com";
System.out.println(str);
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679051716547-bd9133e0-63ee-49d4-a381-8fe7ce70694c.png#averageHue=%23f5f6f2&clientId=u77046ff1-f550-4&from=paste&height=240&id=u150a6ec5&name=image.png&originHeight=300&originWidth=591&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=108855&status=done&style=none&taskId=u3ed0cac2-cc52-4e60-ace4-107e4d58cbe&title=&width=472.8)
<a name="qIkGt"></a>
## 6、主方法分析
`public static void main(String[] args)`

- `public`：描述的是一种执行权限，主方法是一切的开始，一定是公共的
- `static`：程序的执行是通过类名称完成的，所以表示此方法只能由类直接调用
- `void`：主方法是一切的起点，一旦开始就不能返回了
- `main`：是一个系统自定义好的方法名称
- `String[] args`：字符串的名称，可以实现程序启动参数的接收
<a name="uiYmo"></a>
## 7、请解释super和this的区别

- 在程序类汇总使用`this`表示先从本类查找所需要的属性或方法，如果本来不存在则查找父类定义，如果使用`super`则表示不查找子类，直接查找父类
- `this`和`super`都可以进行构造方法的调用，但是`this()`调用的是本类构造，必须放在构造方法的首行，所以不能同时出现。
- `this`可以表示当前对象
<a name="GYR4K"></a>
# Java三大特性
**封装、继承、多态**

- **封装：**是指隐藏对象的属性和实现细节，仅对外提供公共访问方式。将成员变量私有化，对外提供对应的`set`、`get`方法对其进行访问。提高对数据访问的安全性
- **继承：**继承是面向对象最显著的一个特性。继承是从已有的类中派生出新的类，新的类能吸收已有类的数据属性和行为，并能扩展新的能力。在Java中，被继承的类叫做父类或超类，继承父类的类叫子类或派生类。
- **多态：**在面向对象语言中，多态性是指一个方法可以有多种实现版本，即“一种定义，多种实现”。利用多态可以设计和实现可扩展的系统，只要新类也在继承层次中。新的类对程序的通用部分只需进行很少的修改，或不做修改。类的多态性表现为方法的多态性，方法的多态性主要有方法的**重载**和方法的**重写**。
<a name="OO2bv"></a>
## 8、构造方法
```java
class Person{
    private String name;
    private Integer id;
    public Person(String name, Integer id){
        this.name = name;
        this.id = id;
    }
}
```
主要目的：可以通过否早方法实现实例化对象中的属性初始化处理。相当于调用`setter`方法<br />要求如下：

- 构造方法必须与类名保持一致
- 构造方法不允许设置任何的返回值类型，即：没有返回值定义
- 构造方法是在使用关键字`new`实例化对象的时候自动调用的。

结论：一个类至少存在有一个构造方法，永恒存在。若没有手动构造，则类存在一个默认无参构造方法。<br />**为什么构造方法上不孕粗设置返回值类型？**<br />如果在构造方法上使用了void，那么此结构就与普通方法的结构完全相同了，这样编译器就会认为是普通方法。<br />**普通方法和构造方法最大的区别：**<br />构造方法是在类对象实例化的时候调用的，而普通方法是在类对象实例化产生之后调用的。
<a name="Stphn"></a>
## 9、多态性
**多态是同一个行为具有多个不同表现形式的能力**

- **多态性：**多态性是指允许不同子类型的对象对同一消息作出不同的响应。简单的说就是**用同样的对象引用调用同样的方法但是做了不同的事情。**多态性分为**编译时的多态性和运行时的多态性。**如果将对象的方法视为对象向外界提供的服务，那么运行时的多态性可以解释为：当A系统访问B系统提供的服务时，B系统有多种提供服务的方式，但一切对A系统来说都是透明的
- **方法重载（overload）实现的是编译时的多态性（也成为前绑定），而方法重写（override）实现的是运行时的多态性（也成为后绑定）**。运行时多态是面向对象最精髓的东西，要实现多态需要做两件事：①方法重写（子类继承父类并重写父类中已有的或抽象的方法）；②对象造型（用父类引用引用子类型对象，这样同样的引用调用同样的方法就会根据子类对象的不同而表现出不同的行为）。

在Java中对于多态性有两种实现的模式：

1. **方法的多态性**
   1. **方法的重载**
   2. **方法的重写**
2. **对象的多态性：**父子实例之间的转换处理，有两种模式：
   1. **对象向上转型：**

`父类 父类实例 = 子类实例、自动完成转换`<br />提高代码的简洁性，减少代码量<br />**向上转型，也就是创建一个子类对象，把它当作父类来看待，向上转型一定是安全的，但是一旦向上转型为父类对象，就不能调用子类原本特有的方法。**
```java
//DataBaseMessage extends Message
//WebServerMessage extends Message
public static void mian(String args[]){
    fun(new DataBaseMessage());//Message msg=new DataBaseMessage();
    fun(new WebServerMessage());//Message msg=new WebServerMessage();
}
public static void fun(Message msg){
    msg.print();
}

```
```java
package cpm.edu;
class Base
{
    public void method()
    {
        System.out.println("Base");
    } 
}
class Son extends Base
{
    public void method()
    {
        System.out.println("Son");
    }
    
    public void methodB()
    {
        System.out.println("SonB");
    }
}
public class Testw
{
    public static void main(String[] args)
    {
        Base base = new Son();
        base.method();   //Son
//      base.methodB();  //编译错误
    }
}

```
**a.对象向下转型**<br />`子类 子类实例 = （子类）父类实例、强制完成转换`<br />由于向上转型，per是person类型，所以执行子类特有方法fly()时要使用向下转型继续宁墙砖，才能使用SuperMan特有方法：
```java
//SuperMan extends Person
//发生对象的向下转型之前一定要首先发生向上转型
Person per = new SuperMan();//向上转型
per.print();
if(per instanceof SuperMan){//可以使用instanceof关键字判断
    SuperMan man = (SuperMan)per;//向下转型，容易出错
}
System.out.println(man.fly());

```
`instanceof`关键字：可以用来判断某个实例是否是某个类的对象：
```java
Person per = new Person();
System.out.println(per instanceof Person);//true
System.out.println(per instanceof SuperMan);//false

```
<a name="MN9uW"></a>
## 10、覆写和重载时什么意思？

- **覆写（override）是指子类对父类方法的一种重写，**只能比父类抛出更少的异常，访问权限不能比父类小，被覆写的方法不能是`private`的，否则只是在子类中重新定义了一个新方法。
- **重载（overload）表示同一个类中可以有多个名称相同的方法，但这些方法的参数列表各不相同。**

构成重载的条件有哪些？参数类型不同、参数个数不同、参数顺序不同。<br />一般认为，Java中的传递都是值传递，Java中实例对象的传递是引用传递，**Java是值传递的。**<br />**覆写原因：**子类和父类一旦产生继承关系之后，实际上子类会继承父类中的全部定义，但是这里面也有可能出现不合适的场景，子类如果发现父类中设计不足并且需要保留父类中的方法或者属性名称的情况下就会发生覆写。<br />**方法的覆写：**当子类定义了与父类方法名称相同，参数类型即个数完全相同（和父类方法一模一样）的时候，就成为方法的覆写。
```java
class Channel{
    public void connect(){
        System.out.println("父类进行资源的连接。");
    }
}
class DatabaseChannel extends Channel{
    @Override //明确表达该方法是一个覆写的方法，帮助检查出程序错误
    public void connect(){
        System.out.println("子类进行资源的连接。");
    }
}

```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679054143532-3ceb135f-e42e-49f0-822f-56cbbaef8899.png#averageHue=%23fbfaf9&clientId=u77046ff1-f550-4&from=paste&height=230&id=u5dff96a2&name=image.png&originHeight=288&originWidth=742&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=29845&status=done&style=none&taskId=ufd8fd4c8-3556-4aeb-a5cf-cd632738758&title=&width=593.6)
