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
<a name="CoMvw"></a>
## 11、Object类
在Java中只有一个类是不存在有继承关系的，这个类就是`Object`，也就是说所有的类默认情况下都是`Object子类`，以下两种类的定义效果完全相同：
```java
class Person{}
class Person extends Object{}
```
在`Object`设计的时候考虑到了所有的继承问题，所以该类提供有`无参构造方法`，所以子类不会出现构造方法调用失败的语法错误。<br />如果一个程序的方法要求可以接受所有类对象的时候就可以利用`Object`实现处理<br />`Object`类对象可以接受所有数据类型，包括**基本数据类型、类对象、接口对象、数组。**<br />**获取对象信息：**`toString()`<br />`toString()`是`Object`类提供的一个方法，可以获取一个对象的完整信息：
```java
Person per = new Person();
System.out.println(per);//结果为Person@6dle7682，默认使用toString方法。
System.out.println(per.toString());//结果为Person@6dle7682，Object类继承而来。

```
可以直接在对象类中重写此方法：
```java
public String toString(){
    return "姓名："+ this.name + "、年龄：" + this.age;
}

```
<a name="XGCTt"></a>
## 12、抽象类
**基本定义：**抽象类的主要作用在于对子类中覆写方法进行约定，在抽象类里面可以去定义一些抽象方法以实现这样的约定，抽象方法指的是使用了`abstract`关键字定义的并且没有提供方法体的实体，而抽象方法所在的类必须为抽象类，抽象类必须使用`abstract`关键字来定义（在普通方法基础上追加抽象方法就是抽象类）<br />当一个抽象类定义完成之后（抽象类不是完整的类），如果想要去**使用抽象类**，就必须满足以下条件：

- 抽象类必须提供有子类，子类使用`extends`继承一个抽象类
- 抽象类的子类（不是抽象类）一定要覆写抽象类中的全部抽象方法
- 抽象类的对象实例化可以利用对象多态性通过子类向上转型的方法完成

**抽象类自己无法直接实例化**

1. 抽象类中允许没有抽象方法，但即便没有抽象方法，也**无法直接使用关键字new实例化抽象类对象。**
2. 抽象类中**可以提供有static方法，**并且该方法不受到抽象类对象的局限
3. 在定义抽象类的时候绝对**不能使用final关键字**来定义，因为抽象类必须有子类，而final定义的类是不能有子类

抽象类最大的好处：一是对子类方法的统一管理，二是可以自身提供有一些普通方法可以调用抽象方法（这些抽象方法必须在有子类提供实现的时候才会生效）
<a name="NcSB7"></a>
## 13、接口
**接口使用原则：**

- 接口需要被子类实现（`implements`），一个子类可以实现多个父级接口
- 子类（如果不是抽象类），那么一定要覆写接口之中的全部抽象方法
- 接口对象可以利用子类对象的向上转型进行实例化

注意：由于接口描述的是一个公共的定义标准所有在接口之中所有的抽象方法的访问权限都为`public`<br />**先继承后实现：**
```java
class MessageImpl extends Database implements IMessage

```
特点：全部由抽象方法和全局变量所组成
<a name="aaxNe"></a>
### 抽象类与接口的区别
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679116826749-e705f42e-8667-4c59-a85a-999472f31bda.png#averageHue=%23faf9f8&clientId=ud79ad3fc-1a4b-4&from=paste&height=301&id=uaf0d3fed&name=image.png&originHeight=301&originWidth=569&originalType=binary&ratio=1&rotation=0&showTitle=false&size=33206&status=done&style=none&taskId=u7573c93f-8bcc-49f2-b2bd-e20cad0f9b1&title=&width=569)

1. 抽象类可以有构造方法，接口中不能有构造方法
2. 抽象类中可以有普通成员变量，接口中没有普通成员变量
3. 抽象类中可以包含非抽象的普通方法，接口中的所有方法必须都是抽象的，不能有非抽象的普通方法
4. 抽象类中的抽象方法的访问类型可以是`public`、`protected`，但接口中的抽象方法只能是`public`，并且默认为`public abstract`
5. 抽象类中可以包含静态方法，接口中不能包含静态方法
6. 抽象类和接口中都可以包含静态成员变量，抽象类中的静态成员变量的访问类型可以任意，但接口中定义的变量只能是`public static final`，并且默认为`public final`

共同点：

1. 抽象类或接口必须定义子类
2. 子类一定要覆盖抽象类或接口中的全部抽象方法
3. 通过子类的向上转型实现抽象类或接口对象实例化

注意：当抽象类和几口都可以使用的情况下有限要考虑接口，因为接口可以避免子类的单继承局限
<a name="vWpyd"></a>
### 为什么MVC中我们用接口不用抽象类？
抽象类被定义为永远也不能被实例化为具体的对象。它往往用于定义一种抽象上的概念，在类的继承关系中它往往被定义为较上层的位置。在程序设计的实践中，抽象类与接口存在类似的地方，即它更偏重与对共通的方法和属性进行规约。但与接口存在一个非常打的差异则在原，抽象类往往可以规约一个共同的方法和属性时提供一个对他们的实现。以现实世界为例：“水果”可以算作一个抽象类，而“苹果”等则可以作为它的派生类。区别在于，“水果”是个概念，不会有实例，但是“苹果”等则会有实例。
<a name="aibkW"></a>
## 14、泛型
在没有泛型的情况下，通过对类型`Object`的引用类实现参数的“任意化”，“任意化”带来的缺点是要做显式的强制类型转换，而这种转换是要求开发者对实际参数类型可以预知的情况下进行的。对于强制类型转换错误的情况，编译器可能不提示错误，在运行的时候才出现异常，这本身就是一个安全隐患。<br />本质：把类型明确的工作推迟到创建对象或调用方法的时候采取明确的特殊的类型。<br />注意：泛型之中只允许设置引用类型，如果要操作基本类型必须使用包装类。
```java
/*
    1:把泛型定义在类上
    2:类型变量定义在类上,方法中也可以使用
 */
public class ObjectTool<T> {
    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

 //创建对象并指定元素类型
ObjectTool<Integer> tool = new ObjectTool<Integer>();

```
使用泛型可以解决大部分的类对象的强制转换处理，避免了对象的向下转型处理<br />好处：

- 代码更加简洁（不强转）
- 程序更加健壮（只要编译没警告，运行就不会出现`ClassCastException）`
- 可读性和稳定性（在写集合的时候，就限定了类型）
<a name="E5pqE"></a>
### 增强for遍历集合
在创建集合的时候，我们明确了集合的类型，所以我们可以使用增强for来遍历集合
```java
        //创建集合对象
        ArrayList<String> list = new ArrayList<>();

        list.add("hello");
        list.add("world");
        list.add("java");

        //遍历,由于明确了类型.我们可以增强for
        for (String s : list) {
            System.out.println(s);
        }

```
<a name="sLF9c"></a>
###  通配符
通配符“?”只能调用与对象无关的方法，不能调用对象与类型有关的方法。因为直到外界使用才知道具体的类型是什么。也就是说，我是不能使用add()方法的。因为add()方法是把对象丢尽集合中，而现在我是不知道对象的类型是什么
```java
public void test(List<?> list){
    for(int i=0;i<list.size();i++){
        System.out.println(list.get(i));
    }
}

```
?和T都表示不确定的类型，区别在于我们可以对T进行操作，但是对?不行
```java
//指定集合元素只能是T类型
List<T> list = new ArrayList<T>();
//集合元素可以是任意类型，这种没有意义，一般是方法中，实施未来说明用法
List<?> list = new ArrayList<?>();

```
总结：T是一个确定的类型，通常用于泛型类和泛型方法的定义，?是一个而不确定的类型，通常用于泛型方法的调用代码和形参，不能用于定义类和泛型方法。<br />在?这个通配符的基础上还提供有两类晓得通配符

- ? extends ：设置泛型的上限
```java
  |- 例如：定义“？ extends Number”:表示该泛型类型只允许设置Number或Number的子类；

```

- ? super ：设置泛型的下限
```java
定义“？ super Number”:只能够使用String或其父类。

```
区别：上界通配符主要用于读数据，下界通配符主要用于写数据
<a name="SV1An"></a>
## 15、包装类
包装原因：`Object`类最大的特点就是所有的类的父类，并且可以接受所有的数据类型，但是在这个过程中就有个问题：基本数据类型并不是一个类，所以现在如果想将基本数据类型以类的形式进行处理，那么就需要对其进行包装。<br />Java中的基本数据类型没有方法和属性，而包装类就是为了让这些拥有方法和属性，实现对象化交互

- **装箱：**基本数据类型转换为包装类
- **拆箱：**包装类转换为基本数据类型
1. 通过包装类`Integer.toString()`将整型转换为字符串
2. 通过`Integer.parseInt()`将字符串转换为int类型
3. 通过`valueOf()`方法把字符串转换为包装类然后通过自动拆箱
```java
 public static void main(String[] args) {
         //基本数据类型转换为字符串
         int t1=12;
         String t2=Integer.toString(t1);
         System.out.println("int转换为String："+t2);
         //字符串转换为基本数据类型
         //通过paerInt方法
         int t3=Integer.parseInt(t2);
         //通过valeOf,先把字符串转换为包装类然后通过自动拆箱
         int t4=Integer.valueOf(t2);
         System.out.println("t3:"+t3);
         System.out.println("t4:"+t4);
}

```
<a name="kxqV9"></a>
## 16、枚举
主要作用是用于定义有限个对象的一种结构，枚举属于多例设计。<br />使用`enum关键字`来定义枚举类型，其中`FRONT``BEHIND``LEFT``RIGHT`都是枚举项，他们都是本类的实例，本类一共就四个实例对象：
```java
public enum Direction {
    FRONT, 
    BEHIND, 
    LEFT, 
    RIGHT("红色");
}
Direction d = Direction.FRONT;

```
不能使用new来创建枚举类的对象，因为枚举类中的实例就是类中的枚举项，所以在类外只能使用`类名.枚举项`
<a name="n9duq"></a>
### 枚举与switch
在`switch`中，**不能使用枚举类名称。**
```java
Direction d = Direction.FRONT;
  switch(d) {
    case FRONT: System.out.println("前面");break;
    case BEHIND:System.out.println("后面");break;
    case LEFT:  System.out.println("左面");break;
    case RIGHT: System.out.println("右面");break;
    default:System.out.println("错误的方向");
}
Direction d1 = d;
System.out.println(d1);

```
请解释`enum`和`Enum`的区别？

- `enum`：是从JDK1.5之后提供的一个关键字，用于定义枚举类
- `Enum`：是一个抽象类，所以使用`enum`的类就默认继承了此类
<a name="ivMoY"></a>
## 17、异常
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679118626983-f8777eb0-8cbe-4bcc-a149-577845ffea0b.png#averageHue=%23f9f3ed&clientId=ud79ad3fc-1a4b-4&from=paste&height=433&id=uf9702e3e&name=image.png&originHeight=433&originWidth=831&originalType=binary&ratio=1&rotation=0&showTitle=false&size=130873&status=done&style=none&taskId=u13d5ffec-cc48-494f-a30e-4227c70aa9f&title=&width=831)<br />出现异常以后将终止整个程序。为了保证程序出现了非致命错误之后程序依然可以正常运行，所以需要有一个完善的异常处理机制
<a name="bIg8w"></a>
### 处理异常
在Java中进行异常的处理：`try`、`catch`、`finally`
```java
try{
    //可能出现异常的语句
}[catch(异常类型 异常对象){
    //异常处理
}catch(异常类型 异常对象){
    //异常处理
}catch(异常类型 异常对象){
    //异常处理
}...][finally{
    不管异常是否处理都要执行;
}]

```
除此之外，可用的组合有：`try...catch``try...catch...finally``try...finally`<br />如果想要获得非常完整的异常信息，则可以使用异常类中提供的`printStackTrace()`方法<br />对于异常的处理格式也可以在最后追加一个`finally`程序块，表示异常处理后的出口，**不管是否出现异常都要执行。**<br />注意：

- **即便有了异常处理语句，但是如果没有进行正确的异常捕获，那么程序也会导致中断（finally的代码依旧执行），这样的情况应该捕获多个异常**
- **在以后进行多个异常同时处理的时候要把捕获范围大的异常放在捕获范围小的异常之后。**

程序中可以处理的异常的最大的类型就是`Throwable`，而打开`Throwable`可以看到两个子类：

- `Error`：此时程序还未执行出现的错误，开发者无法处理
- `Exception`：程序中出现的异常，开发者可以处理
<a name="SV4Kv"></a>
### throws关键字

- 使用`throws`关键字来进行异常类型的标注，必须有对应的`try...catch`处理
- `throws`表示出现异常的一种可能性，并不一定会发生这些异常
- 异常处理是会一层层往上抛的，直到遇到了某个方法处理了这个异常或者最后抛给了JVM
```java
class MyMath{
    //这个代码执行的时候可能会产生异常，如果产生异常调用处理
    public static int div(int x,int y) throws Exception{
        return x/y;
    }
}
public class JavaDemo{
    public static void main(String args[]) throws Exception{
        try{
             System.out.println(MyMath.div(10,0));
    } catch(Exception e){
        e.printStackTrace();
    }      
  }
}

```
如果主方法继续向上抛异常，那么就表示磁异常将交给JVM负责处理
<a name="ybU0e"></a>
### throw关键字
表示手工进行异常的抛出，即：此时将手工产生一个异常类的实例化对象，并进行异常的抛出处理
```java
class MyMath{
    //异常交给被调用处处理则一定要在方法上使用throws
    public static int div(int x,int y) throws Exception{
        int temp = 0;
        System.out.println("***[START]除法计算开始。");
        try{
            temp = x / y;
        } catch (Exception e){//这里的catch可省略，将会自动向上抛出异常
            throw e;//向上抛出异常对象
        } finally{
            System.out.println("***[END]除法计算结束。");
        }
        return temp;
    }
}
public class JavaDemo{
    public static void main(String args[]){
        try{
            System.out.println(MyMath.div(10,0));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

```
**面试题：请解释throw和throws的区别？**

- `throw`：是在代码块中使用的，主要是手工进行异常的抛出
- `throws`：是在方法定义上使用的，表示将此方法中可能产生的一场明确告诉给调用处，由调用处进行处理
- `throws`表示出现异常的一种可能性，并不一定会发生这些异常；`throw`则是抛出了异常，执行`throw`则一定抛出某些异常

**面试题：请解释RuntimeException与Exception区别？常见的EuntimeException？**

- `RuntimeException`是`Exception`的子类
- `RuntimeException`标注的异常可以**不需要进行强制性try...catch处理。**而`Exception`异常必须进行强制性处理
- 常见的`RuntimeException`异常：`NumberFormatException`、`ClassCastException`、`NullPointException`...
<a name="CLnuh"></a>
### 自定义异常类
分为继承`Exception`和`RuntimeException`两种，如果继承的是`Exception`，则需要进行`try...catch`处理
```java
class BombException extends RuntimeException {
    public BombException(String msg) {
        super(msg);
    }
}
class Food {
    public static void eat(int num) throws BombException {
        if(num > 10) {
            throw new BombException("吃太多了，坏了，肚子要撑爆了。");
        }else {
            System.out.println("正常吃，不怕吃胖。");
        }
    }
}
public class JavaDemo {
    public static void main(String args[]) throws Exception {
        Food.eat(11);
    }
}

```
<a name="u13JS"></a>
### assert断言
从JDK1.4开始追加一个有断言的功能，确定代码执行到某行之后一定是所期待的结果。但断言不一定是准确的，也有可能出现误差，但是这种偏差不应该影响程序的正常执行
```java
public class JavaDemo {
    public static void main(String args[]) throws Exception {
        int x = 10;
        //中间会经过许多的x变量的操作步骤
        assert x == 100 : "x的内容不是100";
        System.out.println(x);
    }
}

```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679119582440-e7dfdb94-9bb3-481a-bf6d-8b315a8d1766.png#averageHue=%23f9f8f7&clientId=ud79ad3fc-1a4b-4&from=paste&height=285&id=u4d151850&name=image.png&originHeight=285&originWidth=528&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23470&status=done&style=none&taskId=ub0d8d9db-4491-43ae-952e-19950dae1c3&title=&width=528)
<a name="XCmrH"></a>
## 18、内部类
将一个类定义在另一个类里面或者方法里面，这种类就被称为**内部类。**
```java
class Outer {  //外部类
    private String msg = "www.mldn.cm";  //私有成员属性
    public void fun() { //普通方法
        Inner in  = new Inner();  //实例化内部类对象
        in.print();  //调用内部类方法
        System.out.println(in.info); //访问内部类的私有属性
    }
    class Inner {  //在Outer类的内部定义了Inner类
        private String info = "今天天气不好，收衣服拉！";
        public void print(){
            System.out.println(Outer.this.msg);  //Outer里类中的属性
        }
    }
}

public class JavaDemo {
    public static void main(String args[]) {
        Outer out = new Outer();  //实例化外部类对象
        out.fun();  //调用外部类中的方法
    }
}

```

- 内部类的优点：轻松的访问外部类中的私有属性
- 缺点：破坏了程序的结构

**注意：内部类可以轻松的访问外部类的私有属性，同理，外部类也可以轻松访问内部类中的私有成员或私有方法**<br />使用了内部类后，内部类与外部类之间的私有操作的访问就不再需要通过`setter`、`getter`以及其他的间接方式完成了，可以直接进行处理操作。<br />内部类本身也属于一个类，虽然大部分情况下内部类往往是被外部类包裹的，但是外部依然可以产生内部类的实例化对象，而此时实例化格式如下：<br />`外部类.内部类 内部类对象 = new 外部类().new 内部类()`
```java
class Outer {  //外部类
    private String msg = "www.mldn.cn" ;  //私有成员属性
    class Inner {  //在Outer类的内部定义了Inner类
        public void print(){
            System.out.println(Outer.this.msg);  //Outer类中的属性
        }
    }
}
public class Java Demo {
    public static void main(String args[]){
        Outer.Inner in = new Outer().new Inner();
        in.print();
    }
}

```
<a name="eht6l"></a>
### static定义内部类
如果说现在在内部类使用了static定义，那么这个内部类就变了“外部类”，static定义的都是独立于类是一个结构，所以该类结构就相当于一个独立的程序类了。<br />**注意：static定义的不管是类还是方法只能够访问static成员，所以static定义的内部类只能访问外部类中的static属性和方法。**<br />这个时候的inner类是一个独立的类，如果此时想要实例化inner对象，只需要根据`外部类.内部类`的结构实例化对象即可，<br />`外部类.内部类 内部类对象 = new 外部类().内部类()`
```java
class Outer {
    private static final String MSG = "www.mldn.cn";
    static class Inner {
        public void print() {
            System.out.println(Outer.MSG);
        }
    }
}
public class JavaDemo {
    public static void main(String args[]){
        Outer.Inner in = new Outer.Inner();
        in.print();
    }
}

```
<a name="zQ6GZ"></a>
### 方法中定义内部类
内部类可以直接访问外部类的私有属性，也可以直接访问方法中的参数，但是对于方法中的参数直接访问是从JDK1.8开始支持的，在JDK1.8之前，如果方法中定义的内部类要想访问方法中的参数则参数前必须追加final。
```java
class Outer {
    private String msg = "www.mldn.cn" ;
    public void fun(long time) {
        class Inner { //内部类
            public void print() {
                System.out.println(Outer.this.msg);
                System.out.println(time);
            }
        }
        new Inner().print(); //方法中直接实例化内部类对象
    }
}
public class JavaDemo {
    public static void main(String args[]) {
        new Outer().fun(23943342L);
    }
}

```
<a name="K2BnM"></a>
### 匿名内部类
匿名内部类是一种简化的内部类的处理形式，其主要是在抽象类和接口的子类上使用的。与内部类相比，匿名内部类只是一个没有名字的只能够使用一次的，并且结构固定的一个子类操作
```java
interface IMessage {
    public void send(String str);
}
public class JavaDemo {
    public static void main(String args[]) {
        IMessage msg = new IMessage() {  //匿名内部类
            public void send(String str) {
                System.out.println(str);
            }
        };
        msg.send("www.mldn.cn");
    }
}

```
<a name="ZHpqn"></a>
## 19、基本数据类型和引用数据类型
Java中的数据类型分为两大类，基本数据类型和引用数据类型。
<a name="Xbs3L"></a>
### 基本数据类型

- 整型：`long``int``short``byte`
- 浮点类型：`float``double`
- 字符类型：`char`
- 布尔类型：`boolean`

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679120334054-6b8fcc3e-3f3a-45e9-8f5f-540d6349a67d.png#averageHue=%23faf9f9&clientId=ud79ad3fc-1a4b-4&from=paste&height=632&id=u84f66795&name=image.png&originHeight=632&originWidth=512&originalType=binary&ratio=1&rotation=0&showTitle=false&size=41572&status=done&style=none&taskId=ua1fc0941-7c3a-4286-887f-0f3130833b8&title=&width=512)
<a name="ZgdNj"></a>
### 引用数据类型
引用数据类型非常多，大致包括：类、接口类型、数组类型、枚举类型、注解类型、字符串型
<a name="hyUoG"></a>
### 基本数据类型和引用数据类型的区别
1、存储位置

- 基本数据类型：在方法中定义的非全局基本数据类型变量的具体内容存储在栈中
- 引用数据来行：只要是引用数据类型，其具体内容都是存放在堆中的，而栈中存放的是其具体内容所在内存的地址

2、传递方式

- 基本变量类型：在方法中定义的非全局基本数据类型变量，调用方法时作为参数是按值传递的
- 引用数据类型：调用方法时作为参数是引用传递的
<a name="LldF2"></a>
## 20、值传递和引用传递
值传递指的是在方法调用时，传递的参数是按值的拷贝传递
```java
public static void main(String[] args) {
    int a=1;
    change(a);
    System.out.println("交换a后的值:"+a);
}

private static void change(int a) {
    a=1111;
    System.out.println("交换方法里面的a:"+a);
}
//运行输出
//交换方法里面的a:1111
//交换a后的值:1
//按值传递重要特点：传递的是值的拷贝，也就是说传递后就互不相关了。

```
引用传递指的是在方法调用时，传递的参数是按引用进行传递，其实传递的引用的地址，也就是变量所对应的内存空间的地址。
```java
public static void main(String[] args) {
    Person p=new Person();
    p.setAge(25);
    changeAge(p);
    System.out.println("改变年龄后的年龄:"+p.getAge());
}

private static void changeAge(Person p) {
    p.setAge(35);
    System.out.println("改变年龄方法里面的年龄:"+p.getAge());
}
//运行输出
//改变年龄方法里面的年龄:35
//改变年龄后的年龄:35

class Person{
    private int age=10;
    public int getAge() {
           return age;
    }
    public void setAge(int age) {
           this.age = age;
    }
}

```
传递的是值的引用，也就是说传递前后都指向同一个引用（也就是同一个内存空间），引用类型的传递后的修改会影响原来的值。
<a name="Rl5Mc"></a>
## 21、get和post请求的区别

- get请求用来从服务器上获得资源，而post是用来向服务器提交数据
- get将表单中数据按照`name=value`的形式，添加到action所指向的url后面，并且两者使用？连接，而各个变量之间使用&连接；post是将表单中的数据放在HTTP协议的请求头或消息体中，传递到action所指向的url
- get传输的数据要受到url长度限制（1024字节）；而post可以传输大量的数据，上传文件通常要使用post方式
- 使用get时参数会显示在地址栏上，如果这些数据不是敏感数据，那么使用get；对于敏感数据还是使用post
- get请求只能接受url编码，而post请求支持多种编码
- get只接受ascll字符，而post没有限制
<a name="curzX"></a>
## 22、请求转发和重定向
`forward`又叫转发，`redirect`叫做重定向

- 从地址栏显示来说
   - `forward`是服务器内部的重定向，服务器直接访问目标地址的url网址
   - `redirect`是服务器根据逻辑，发送一个状态码，告诉浏览器重新去请求那个地址，所以地址栏显示的是新的地址
- 从数据共享来说
   - `forward`会将request的信息带到被重定向的jsp或者servlet中使用。即可以共享数据
   - `redirect`不能共享
- 从效率来说
   - `forward`效率高
   - `redirect`效率低
- 从本质来说
   - `forward`转发是服务器上的行为
   - `redirect`重定向是客户端的行为
- 从请求次数来说
   - `forward`只有一次请求
   - `redirect`有两次请求
<a name="Rak9N"></a>
## 23、final关键字
在Java中，`final`关键字可以用来修饰**类、方法和变量（包括成员变量和局部变量）**

1. **修饰类**
   1. 当用final修饰一个类时，表明这个类**不能被继承。**也就是说，如果一个类永远不会让他被继承，就可以使用final修饰
2. **修饰方法**
   1. 使用final方法的原因有两个。第一个原因是**把方法锁定，**以防任何继承类修改它的含义；第二个原因是**效率，**只有在想明确禁止该方法在子类中被覆盖的情况下才将方法设置为final的。
3. **修饰变量**
   1. 对于一个final变量，如果是**基本数据类型的变量，**则其数值一旦在初始化之后便**不能更改；**如果是**引用类型**的变量，则在其初始化之后便**不能再让其指向另一个对象，但是它指向的对象的内容是可变的。**
<a name="WKvgL"></a>
## 24、强引用、软引用、弱引用和虚引用的区别

1. **强应用，**最普遍的一种引用方式，如`String s = "abc"`，变量s就是字符串"abc"的强引用，只要强引用存在，则垃圾回收器就不会回收这个对象
2. **软引用，**用于描述还有用但非必须的对象，如果内存足够，不回收，如果内存不足，则回收。一般用于实现内存敏感的告诉缓存，软引用可以和引用队列`ReferenceQueue`联合使用，如果软引用的对象被垃圾回收，JVM就会把这个软引用加入到与之关联的引用队列中。
3. **弱引用，**弱引用和软引用大致相同，弱引用和软引用的区别在于：只具有弱引用和对象拥有更短暂的生命周期。在垃圾回收器县城扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间是否足够，都会回收他的内存。短时间内通过弱引用取对应的数据，可以取到，当执行过第二次垃圾回收时，将返回null。弱引用主要用于监控对象时候已经被垃圾回收期标记为即将回收的垃圾，可以通过弱引用的`isEnQueued`方法返回对象是否被垃圾回收器标记。
4. **虚引用，**就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列联合使用。当垃圾回收器准备回收一个对象时，如果发现还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
<a name="gp5AG"></a>
## 25、String类常用方法
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202666544-59bb9b84-13ae-49ec-83c7-4e4a713474c1.png#averageHue=%23faf9f8&clientId=u150947cb-4235-4&from=paste&height=272&id=uf9f2c495&name=image.png&originHeight=272&originWidth=495&originalType=binary&ratio=1&rotation=0&showTitle=false&size=33099&status=done&style=none&taskId=ud8007804-53b9-4df0-9a84-683ff044c58&title=&width=495)
```java
public static void main(String[] args) {
    String str = "helloworld";
    char c=str.charAt(4); //charAt获取某一个指定索引位置的字符,从0开始
    System.out.println(c); //输出"o"
    char[] result=str.toCharArray();//将String转为字符存入数组中
    System.out.println(Arrays.toString(result));
    for (int i = 0; i < result.length; i++) {
        result[i]-=32;//小写变大写，编码减少32
    }
    //将传入的全部字符数组变为字符串
    String newStr = new String(result);
    System.out.println(newStr);
    //将部分字符数组变为字符串
    System.out.println(new String(result,0,5));
}
/* 输出结果：
o
[h, e, l, l, o, w, o, r, l, d]
HELLOWORLD
HELLO
*/

```
<a name="nivlt"></a>
### 字符串比较方法
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202722370-10fdb8b5-2b02-4f0e-86d7-7448664ab718.png#averageHue=%23faf9f9&clientId=u150947cb-4235-4&from=paste&height=324&id=ue61af01d&name=image.png&originHeight=324&originWidth=496&originalType=binary&ratio=1&rotation=0&showTitle=false&size=39803&status=done&style=none&taskId=ueddfb2b0-c699-402c-8832-a711b44a8c9&title=&width=496)
```java
String strA = "mldn";
String strB = "mldN";
System.out.println(strA.compareTo(strB)); // 32
System.out.println(strB.compareTo(strA)); // -32
System.out.println("Hello".compareTo("Hello")); // 0
//忽略大小写
System.out.println(strA.compareToIgnoreCase(strB)); // 0

```
<a name="bx5ce"></a>
### 字符串查找
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202759612-13f04e5c-6dc9-4a66-a9cb-846904c93aba.png#averageHue=%23faf9f8&clientId=u150947cb-4235-4&from=paste&height=500&id=u4b1ff284&name=image.png&originHeight=500&originWidth=500&originalType=binary&ratio=1&rotation=0&showTitle=false&size=67111&status=done&style=none&taskId=u76a69d04-a617-4c5c-9722-defedba43e8&title=&width=500)
```java
String str = "**@@www.mldn.cn##";
//判断子字符串是否存在
System.out.println(str.contains("mldn"));  //true
//查找指定字符串的位置
System.out.println(str.indexOf("w"));  //4
//由后向前查找指定字符串的位置
System.out.println(str.lastIndexOf("w"));  //6
//判断是否以指定的字符串开头
System.out.println(str.startsWith("**"));  //true
//判断由指定位置判断是否以指定字符串开头
System.out.println(str.startsWith("@@",2));  //true
//判断是否以指定的字符串"##"结尾
System.out.println(str.endsWith("##"));  //true

```
<a name="Kwne3"></a>
### 字符串替换
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202816910-0de6f87a-2433-48a3-b3b4-1e99fc62ba4b.png#averageHue=%23fbfaf9&clientId=u150947cb-4235-4&from=paste&height=160&id=u5d65db49&name=image.png&originHeight=160&originWidth=490&originalType=binary&ratio=1&rotation=0&showTitle=false&size=18863&status=done&style=none&taskId=u176f64fc-9941-4cc5-9a1b-6526178821c&title=&width=490)
```java
String str = "www.mldn.cn##";
//将"w"全部替换为"X"
System.out.println(str.replaceAll("w", "X"));  //XXX.mldn.cn##
////将第一个"w"替换为"X"
System.out.println(str.replaceFirst("w", "X")); //Xww.mldn.cn##

```
<a name="qbp0K"></a>
### 字符串拆分
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202801148-0dd1b07d-f65f-4b06-8e87-62fd70dca8d9.png#averageHue=%23fbfaf9&clientId=u150947cb-4235-4&from=paste&height=158&id=u1732809b&name=image.png&originHeight=158&originWidth=499&originalType=binary&ratio=1&rotation=0&showTitle=false&size=20212&status=done&style=none&taskId=udaee96ea-f122-4d46-b5ff-596fd85bc15&title=&width=499)
```java
String str = "hello world world mldn";
//按空格将字符串全部拆分，以字符串数组形式返回
String result[] = str.split(" "); 
for (int x = 0; x < result.length; x++) {
    System.out.println(result[x]);
}
/* hello
 * world
 * world
 * mldn
 * */
//按空格将字符串拆分，截止第二个字符串
String results[] = str.split(" ",2);
for (int x = 0; x < results.length; x++) {
    System.out.println(results[x]);
}
/*
 * hello
 * world world mldn
 * */

```
遇到正则表达式拆不了的，需要使用转义字符`\\`
```java
String str = "192.168.1.2";
//按空格将字符串全部拆分，以字符串数组形式返回
String result[] = str.split("\\."); 
for (int x = 0; x < result.length; x++) {
    System.out.println(result[x]);
}

```
<a name="F1Cqd"></a>
### 字符串截取
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202896966-103572f0-d44e-4999-94bb-2915b566a28a.png#averageHue=%23fbfaf9&clientId=u150947cb-4235-4&from=paste&height=162&id=u1937c43f&name=image.png&originHeight=162&originWidth=501&originalType=binary&ratio=1&rotation=0&showTitle=false&size=19545&status=done&style=none&taskId=uea443141-66f4-4919-b8f1-dcdfaaca50b&title=&width=501)
```java
String str = "www.zbdx.cn";
//从下标4开始截取字符串
System.out.println(str.substring(4));//zbdx.cn
//截取下标为4-8的字符串
System.out.println(str.substring(4,8));//zbdx

```
<a name="yRiqa"></a>
### 其他方法
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679202912214-0a574cd9-248c-4a11-98bd-a653bf017336.png#averageHue=%23fafaf9&clientId=u150947cb-4235-4&from=paste&height=431&id=uac35773f&name=image.png&originHeight=431&originWidth=502&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40997&status=done&style=none&taskId=uff471ada-bddd-4272-8263-d3cf273566e&title=&width=502)
```java
String strA = "www.mldn.cn";
//字符串的连接
String strB = "www.".concat("mldn").concat(".cn");
System.out.println(strB);//www.mldn.cn
System.out.println(strA==strB);//false

String str = "";
//判断是否为空
System.out.println(str.isEmpty());//true

String str = "  Hello World!  ";
System.out.println(str.length());//19
//去除左右两边空格,中间空格无法消除
String trimStr = str.trim();
System.out.println(str); //  Hello World!  
System.out.println(trimStr); //Hello World!

String str = "Hello World";
//转大写
System.out.println(str.toUpperCase());//HELLO WORLD
//转小写
System.out.println(str.toLowerCase());//hello world

```
<a name="aUgSK"></a>
## 26、Scanner方法
`Scanner`类的主要功能是简化文本扫描，这个类最实用的地方在**获取控制台输入。**<br />`Scanner`类里面有如下几种操作方法：

- 构造：`public Scanner(InputStream source)`
- 判断是否有数据：`public boolean hasNext()`
- 取出数据：`public String next()`
- 设置分隔符：`public Scanner useDelimiter(String pattern)`
```java
public static void main(String[] args) {
    //实现键盘数据输入
    Scanner scan = new Scanner(System.in);
    System.out.print("请输入年龄:");
    //判断是否有整数输入
    if(scan.hasNextInt()) {
        //直接获取数字
        int age = scan.nextInt();
        System.out.println("您的年龄:"+age);
    }else {
        System.out.println("输入格式错误！");
    }
    scan.close();
}

```
```java
public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("请输入信息:");
    //判断是否有字符串输入
    if(scan.hasNext()) {
        //直接获取字符串
        String msg = scan.next();
        System.out.println("信息为:"+msg);
    }
    scan.close();
}

```
使用正则进行判断：
```java
public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("请输入您的生日:");
    //判断是否有生日输入
    if(scan.hasNext("\\d{4}-\\d{2}-\\d{2}")) {
        String str = scan.next();
        System.out.println("生日为:"+str);
    }else {
        System.out.println("输入格式错误！");
    }
    scan.close();
}

```
读取文件内容：
```java
public static void main(String[] args) throws FileNotFoundException {
    Scanner scan = new Scanner(new File("D:"+File.separator+"mldn-info.txt"));;
    scan.useDelimiter("\n");//设置读取分隔符
    while(scan.hasNext()) {
        System.out.println(scan.next());
    }
    scan.close();
}

```
<a name="LdKom"></a>
## 27、序列化
所谓的序列化指的是将内存中保存的对象以二进制数据流的形式进行处理，可以实现对象的保存或者是网络传输。
<a name="oRYk5"></a>
### 对象序列化
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679203303624-4a17ea16-2a02-47ae-912a-c5e62dbebe03.png#averageHue=%23f4f2f1&clientId=u150947cb-4235-4&from=paste&height=223&id=uad9db5ca&name=image.png&originHeight=223&originWidth=870&originalType=binary&ratio=1&rotation=0&showTitle=false&size=98321&status=done&style=none&taskId=u6ec73e59-c2b4-4da6-918d-89e9e4292d4&title=&width=870)<br />然后并不是所有的对象都可以被系列化，在Java里面有一个强制性的要求：如果要序列化的对象，那么对象所在的类一定要实现`java.io.Serializable`父接口，作为序列化的标记，这个接口并没有任何的方法，因为他描述的是一种类的能力
```java
public class JavaAPIDemo{
        public static void main(String[] args) {
        }
}
    
@SuppressWarnings("serial")
//Person类可以被序列化
class Person implements Serializable {
    private String name;
    private int age;

    private Person(String name,int age) {
        this.name = name;
        this.age = age;
    }
    //setter、getter略
    @Override
    public String toString() {
        return "姓名："+this.name + "年龄："+ this.age;
    }
}

```
此时`Person`类产生的每一个对象都可以实现二进制的数据传输，属于可以被序列化的程序类
<a name="lObyq"></a>
## 28、String类方法
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679203473759-59c79e7e-dbf8-485b-acf0-1cb267658929.png#averageHue=%23f9f8f6&clientId=u150947cb-4235-4&from=paste&height=802&id=u21731a32&name=image.png&originHeight=802&originWidth=504&originalType=binary&ratio=1&rotation=0&showTitle=false&size=129947&status=done&style=none&taskId=u0e9026ea-b22f-44e5-8b04-c668886bc99&title=&width=504)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679203486007-a4587838-1cde-47d4-9789-01d1dd3dd02a.png#averageHue=%23faf8f6&clientId=u150947cb-4235-4&from=paste&height=873&id=ud335dae7&name=image.png&originHeight=873&originWidth=499&originalType=binary&ratio=1&rotation=0&showTitle=false&size=131870&status=done&style=none&taskId=ue591d3c7-000a-44c1-9c18-2abe6337875&title=&width=499)![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1679203499011-038c18cd-99c6-42ee-ac68-65b0cae9dce8.png#averageHue=%23f9f8f6&clientId=u150947cb-4235-4&from=paste&height=849&id=u411a7e4f&name=image.png&originHeight=849&originWidth=502&originalType=binary&ratio=1&rotation=0&showTitle=false&size=133097&status=done&style=none&taskId=u296062ce-3eb7-43e0-9d84-89361f10b14&title=&width=502)

<a name="bgG6l"></a>
## 29、定时任务Spring Task
如果使用`Spring`或`Spring Boot`框架，可以直接使用`Spring Framework`自带的定时任务<br />以`Spring Boot`为例，实现定时任务只需要两步：

1. 开启定时任务
2. 添加定时任务
<a name="Aciz9"></a>
### 开启定时任务
开启定时任务只需要在`Spring Boot`的启动类上声明`@EnableScheduling`即可
```java
@SpringBootApplication
@EnableScheduling // 开启定时任务
public class DemoApplication {
    // do someing
}

```
<a name="OugFu"></a>
### 添加定时任务
定时任务的添加只需要使用`@Scheduled`注解标注即可，如果有多个定时任务可以创建哎你多个`@Scheduled`注解标注的方法，
```java
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
@Component // 把此类托管给 Spring，不能省略
@ConditionalOnProperty(prefix = "opent.evaluationTimeout.scheduling", name = "enabled", havingValue = "true")//当某个组件没有属性可以控制是否在项目中动态的控制生效，则可以使用该注解
public class TaskUtils {
    // 添加定时任务
    @Scheduled(cron = "59 59 23 0 0 5") // cron 表达式，每周五 23:59:59 执行
    public void doTask(){
        System.out.println("我是定时任务~");
    }
}

```
<a name="sFuNr"></a>
## 30、Lambda表达式
`Lambda`表达式是JDK1.8的一个新特性，可以取代大部分的匿名内部类，写出更优雅的Java代码，尤其是在集合的遍历和其他集合操作中，可以极大地优化代码结构。<br />JDK也提供了大量的内置函数式接口供我们使用，使得`Lambda`表达式的运用更加方便、高效。
<a name="XkhZr"></a>
### @FunctionlInterface
修饰函数式接口的，要求接口中的抽象方法只有一个。这个注解往往会和`Lambda`表达式一起出现。
<a name="IU0bS"></a>
### Lambda基础语法
```java
/**多参数无返回*/
@FunctionalInterface
public interface NoReturnMultiParam {
    void method(int a, int b);
}

/**无参无返回值*/
@FunctionalInterface
public interface NoReturnNoParam {
    void method();
}

/**一个参数无返回*/
@FunctionalInterface
public interface NoReturnOneParam {
    void method(int a);
}

/**多个参数有返回值*/
@FunctionalInterface
public interface ReturnMultiParam {
    int method(int a, int b);
}

/*** 无参有返回*/
@FunctionalInterface
public interface ReturnNoParam {
    int method();
}

/**一个参数有返回值*/
@FunctionalInterface
public interface ReturnOneParam {
    int method(int a);
}

```
语法形式为`() -> {}`，其中`()`用来描述参数列表，`{}`用来描述方法体，`->`为`Lambda运算符`，读作（goes to）
```java
import lambda.interfaces.*;

public class Test1 {
    public static void main(String[] args) {

        //无参无返回
        NoReturnNoParam noReturnNoParam = () -> {
            System.out.println("NoReturnNoParam");
        };
        noReturnNoParam.method();

        //一个参数无返回
        NoReturnOneParam noReturnOneParam = (int a) -> {
            System.out.println("NoReturnOneParam param:" + a);
        };
        noReturnOneParam.method(6);

        //多个参数无返回
        NoReturnMultiParam noReturnMultiParam = (int a, int b) -> {
            System.out.println("NoReturnMultiParam param:" + "{" + a +"," + + b +"}");
        };
        noReturnMultiParam.method(6, 8);

        //无参有返回值
        ReturnNoParam returnNoParam = () -> {
            System.out.print("ReturnNoParam");
            return 1;
        };

        int res = returnNoParam.method();
        System.out.println("return:" + res);

        //一个参数有返回值
        ReturnOneParam returnOneParam = (int a) -> {
            System.out.println("ReturnOneParam param:" + a);
            return 1;
        };

        int res2 = returnOneParam.method(6);
        System.out.println("return:" + res2);

        //多个参数有返回值
        ReturnMultiParam returnMultiParam = (int a, int b) -> {
            System.out.println("ReturnMultiParam param:" + "{" + a + "," + b +"}");
            return 1;
        };

        int res3 = returnMultiParam.method(6, 8);
        System.out.println("return:" + res3);
    }
}

```
<a name="bfsf1"></a>
### Lambda语法简化
```java
public class Test2 {
    public static void main(String[] args) {

        //1.简化参数类型，可以不写参数类型，但是必须所有参数都不写
        NoReturnMultiParam lamdba1 = (a, b) -> {
            System.out.println("简化参数类型");
        };
        lamdba1.method(1, 2);

        //2.简化参数小括号，如果只有一个参数则可以省略参数小括号
        NoReturnOneParam lambda2 = a -> {
            System.out.println("简化参数小括号");
        };
        lambda2.method(1);

        //3.简化方法体大括号，如果方法条只有一条语句，则可以省略方法体大括号
        NoReturnNoParam lambda3 = () -> System.out.println("简化方法体大括号");
        lambda3.method();

        //4.如果方法体只有一条语句，并且是 return 语句，则可以省略方法体大括号
        ReturnOneParam lambda4 = a -> a+3;
        System.out.println(lambda4.method(5));

        ReturnMultiParam lambda5 = (a, b) -> a+b;
        System.out.println(lambda5.method(1, 1));
    }
}

```
<a name="lsI2t"></a>
### Lambda表达式常用示例
有时候我们不是必须要字节重写某个匿名内部类的方法，我们可以利用`Lambda`表达式的接口快速指向一个已经被实现的方法。<br />`方法归属者::方法名` 静态方法的归属者为类名，普通方法归属者为对象
```java
public class Exe1 {
    public static void main(String[] args) {
        ReturnOneParam lambda1 = a -> doubleNum(a);
        System.out.println(lambda1.method(3));

        //lambda2 引用了已经实现的 doubleNum 方法
        ReturnOneParam lambda2 = Exe1::doubleNum;
        System.out.println(lambda2.method(3));

        Exe1 exe = new Exe1();

        //lambda4 引用了已经实现的 addTwo 方法
        ReturnOneParam lambda4 = exe::addTwo;
        System.out.println(lambda4.method(2));
    }

    /**
     * 要求
     * 1.参数数量和类型要与接口中定义的一致
     * 2.返回值类型要与接口中定义的一致
     */
    public static int doubleNum(int a) {
        return a * 2;
    }

    public int addTwo(int a) {
        return a + 2;
    }
}

```
<a name="XRCG4"></a>
### 构造方法的引用
一般我们需要声明接口，该接口作为对象的生成器，通过`类名::new`的方式来实例化对象，然后调用方法返回对象
```java
interface ItemCreatorBlankConstruct {
    Item getItem();
}
interface ItemCreatorParamContruct {
    Item getItem(int id, String name, double price);
}

public class Exe2 {
    public static void main(String[] args) {
        ItemCreatorBlankConstruct creator = () -> new Item();
        Item item = creator.getItem();

        ItemCreatorBlankConstruct creator2 = Item::new;
        Item item2 = creator2.getItem();

        ItemCreatorParamContruct creator3 = Item::new;
        Item item3 = creator3.getItem(112, "鼠标", 135.99);
    }
}

```
<a name="LLLYE"></a>
### Lambda表达式创建线程
我们以往都是通过创建`Thread`对象，然后通过匿名内部类重写`run()`方法，一提到匿名内部类我们就应该想到可以使用`Lambda`表达式简化线程的创建
```java
    Thread t = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        System.out.println(2 + ":" + i);
      }
    });
      t.start();

```
<a name="dDaAc"></a>
### 遍历集合
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    //....
}

ArrayList<Integer> list = new ArrayList<>();

Collections.addAll(list, 1,2,3,4,5);

//lambda表达式 方法引用
list.forEach(System.out::println);

list.forEach(element -> {
if (element % 2 == 0) {
    System.out.println(element);
}
});

```
<a name="JWePX"></a>
## 31、BigDecimal
Java在`java.math`包中提供的API类`BigDecimal`，用来对超过16为有效位的书进行精确的运算
<a name="Ih6d5"></a>
### 方法描述

- `add(BigDecimal)` `BigDecimal`对象中的值相加，然后返回这个对象。
- `subtract(BigDecimal)``BigDecimal`对象中的值相减，然后返回这个对象。
- `multiply(BigDecimal)` `BigDecimal`对象中的值相乘，然后返回这个对象。
- `divide(BigDecimal)` `BigDecimal`对象中的值相除，然后返回这个对象。
- `toString()` 将`BigDecimal`对象的数值转换成字符串。
- `doubleValue()` 将`BigDecimal`对象中的值以双精度数返回。
- `floatValue()` 将`BigDecimal`对象中的值以单精度数返回。
- `longValue()` 将`BigDecimal`对象中的值以长整数返回。
- `intValue()` 将`BigDecimal`对象中的值以整数返回。
<a name="gLwtN"></a>
### 四舍五入

- `BigDecimal.setScale()`方法用于格式化小数点
- `setScale(1)`表示保留一位小数，默认用四舍五入方式
- `setScale(1,BigDecimal.ROUND_DOWN)`直接删除多余的小数位，如2.35会变成2.3
- `setScale(1,BigDecimal.ROUND_UP)`进位处理，2.35变成2.4
- `setScale(1,BigDecimal.ROUND_HALF_UP)`四舍五入，2.35变成2.4
- `setScaler(1,BigDecimal.ROUND_HALF_DOWN)`四舍五入，2.35变成2.3，如果是5则向下舍
- `setScaler(1,BigDecimal.ROUND_CEILING)`接近正无穷大的舍入
- `setScaler(1,BigDecimal.ROUND_FLOOR)`接近负无穷大的舍入，数字`>0`和`ROUND_UP`作用一样，数字`<0`和`ROUND_DOWN`作用一样
- `setScaler(1,BigDecimal.ROUND_HALF_EVEN)`向最接近的数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
```java
//保留两位小数并向上取整
BigDecimal assemblyPrice = assemblyPrice.setScale(2,BigDecimal.ROUND_HALF_UP);

```
<a name="QlQo3"></a>
### BigDecimal数据的比较
此方法的返回类型为int，它可以返回任何给定值<br />`num.compareTo(BigDecimal.ZERO)==0` num等于0时，返回 0<br />`num.compareTo(BigDecimal.ZERO) > 0` num大于0时，返回 1<br />`num.compareTo(BigDecimal.ZERO) < 0` num小于0时，返回 -1
<a name="FVGlU"></a>
## 32、校验一个String类型的变量是否为空
在校验一个String类的变量是否为空时，通常存在三种情况：

- 是否为null
- 是否为""
- 是否为空字符串，如“ ”

`StringUtils`的`isBlank()`方法可以一次性校验这三种情况，返回值都是true，否则为false

