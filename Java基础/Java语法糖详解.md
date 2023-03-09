<a name="wZa7Z"></a>
## 什么是语法糖？
**语法糖**也称糖衣语法，指在计算机语言中修改添加的某种语法，这种语法对语言的功能并没有影响，但是更方便程序员使用。简而言之，语法糖让程序更加简介，有更高的可读性。
<a name="vCUFF"></a>
## Java中有哪些常见的语法糖
其实，**Java虚拟机并不支持这些语法糖。这些语法糖在编译阶段就会被还原成简单的基础语法结构，这个过程就是解语法糖。**<br />说到编译，大家肯定都知道，Java语言中，`javac`命令可以讲后缀`.java`的源文件编译为后缀名为`.class`的可以运行在Java虚拟机的字节码。如果你去看`com.sun.tools.javac.main.JavaCompiler`的源码，你会发现在`compile()`中有一个步骤就是调用`desuger()`，这个方法就是负责解语法糖的实现的。<br />Java中最常用的语法糖主要是泛型、变长参数、条件编译、自动拆装箱、内部类等。
<a name="eU2zj"></a>
### switch支持String与枚举
前面提到过，从Java7开始，Java语言中的语法糖在逐渐丰富，其中一个比较重要的就是java7中`switch`开始支持`String`。<br />Java中的`switch`自身原本就支持基本类型。比如`int`、`char`等。对于`int`类型，直接进行树枝的比较。对于`char`类型则是比较其ASCLL码。所以，对于编译器来说，`switch`中其实只能使用整型，任何类型的比较都要转换成整型。比如`byte`、`short`、`char`以及`int。`<br />下面是`switch`对`String`的支持的代码：
```java
public class switchDemoString {
    public static void main(String[] args) {
        String str = "world";
        switch (str) {
        case "hello":
            System.out.println("hello");
            break;
        case "world":
            System.out.println("world");
            break;
        default:
            break;
        }
    }
}

```
反编译之后内容如下：
```java
public class switchDemoString
{
    public switchDemoString()
    {
    }
    public static void main(String args[])
    {
        String str = "world";
        String s;
        switch((s = str).hashCode())
        {
        default:
            break;
        case 99162322:
            if(s.equals("hello"))
                System.out.println("hello");
            break;
        case 113318802:
            if(s.equals("world"))
                System.out.println("world");
            break;
        }
    }
}
```
看到这个代码。你知道原来**字符串的switch是通过**`equals()`**和 **`hashCode()`**方法来实现的。**还好`hashCode()`方法返回的是`int`，而不是`long`。<br />进行`switch`的实际是哈希值，然后通过使用`equals()`方法比较进行安全检验，这个检查是必要的，因为哈希可能会发生碰撞。
<a name="kwRdH"></a>
### 泛型
通常情况下，一个编译器处理泛型有两种方式：`Code specialization`和`Codesharing`。C++和C#使用`Code specialization`的处理机制，而Java使用的是`Code sharing`的机制。<br />Code sharing 方式为每个泛型类型创建唯一的字节码表示，并且讲该泛型类型的实例都映射到这个唯一的字节码表示上。将多种泛型类型实例映射到唯一的字节码表示是通过类型擦除（`type erasue）`实现的。<br />也就是说，对于Java虚拟机来说，他根本不认识`Map<String, String> map`这样的语法。需要在编译阶段通过类型擦除的方式进行解语法糖。<br />类型擦除的主要过程如下：1、将所有的泛型参数用其最左边界（最顶级的夫类型）类型替换。2、溢出所有的类型参数。<br />如：
```java
Map<String, String> map = new HashMap<String, String>();
map.put("name", "zhangsan");
map.put("wechat", "zhangsan");
map.put("blog", "www.zhangsan.com");
```
解语法糖后变成：
```java
Map map = new HashMap();
map.put("name", "Zhangsan");
map.put("wechat", "zhangsan");
map.put("blog", "www.zhangsan.com");
```
**虚拟机中没有泛型，只有普通类和普通方法，所有泛型类的类型参数在编译时都会被擦除，泛型类并没有自己独有的**`Class`**类对象。比如并不存在**`List<String>.class`或是`List<Integer>.class`，**而只有**`List.class`。
<a name="j8cLu"></a>
### 自动装箱和拆箱

