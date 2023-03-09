开始之前，我们需要搞懂下面这两个概念

- 形参&实参
- 值传递&引用传递
<a name="Hs4lR"></a>
## 形参&实参
方法的定义有可能会用到**参数**，参数在程序语言中分为：

- **实参（Arguments）：**用于传递给函数/方法的的参数，必须有确定的值。
- **形参（Paraments）：**用于定义函数/方法，接收实参，不需要有确定的值。
```java
String hello = "Hello!";
// hello为实参
sayHello(hello);
// str 为形参
void sayHello(String str){
    System.out.println(str);
}
```
<a name="oCKJs"></a>
## 值传递&引用传递
程序设计语言将实参传递给方法/函数，的方式分为两种：

- **值传递：**方法接收的是实参值的拷贝，会创建副本。
- **引用传递：**方法接收的直接是实参所引用的对象在堆中的地址，不会创建副本，对形参的修改将影响到实参。

很多程序语言（C++等）都提供了引用传递，但是**在Java中只有值传递。**
<a name="jQTcx"></a>
## 为什么说Java中只有值传递？
<a name="sUTpU"></a>
### 案例1：传递基本类型参数
```java
public static void main(String[] args){
    int num1 = 10;
    int num2 = 20;
    swap(num1, num2);
    System.out.println("num1" + num1);
    System.out.println("num2" + num2);
}

public static void swap(int a, int b){
    int temp = a;
    a = b;
    b = temp;
    System.out.println("a = " + a);
    System.out.println("b = " + b);
}
```
输出：
```
a = 20
b = 10
num1 = 10
num2 = 20;
```
解析：num1 和 num2 是实参 ， a 和 b 是形参。形参的值并不会改变实参的值，a和b的值只是从num1和num2中复制过来的。<br />**我们知道了一个方法不能修改一个基本数据类型的参数。**
<a name="cmgqz"></a>
### 案例2：传递引用类型参数1
```java
public static void main(String[] args) {
    int[] arr = { 1, 2, 3, 4, 5 };
    System.out.println(arr[0]);
    change(arr);
    System.out.println(arr[0]);
}

public static void change(int[] array) {
    // 将数组的第一个元素变为0
    array[0] = 0;
}
```
输出：
```java
1
0
```
解析：在这里可能有很多同学认为是引用传递，但其实不是的，这里也是值传递，只不过传递的值是**地址**，也就是说change方法的参数拷贝的是arr的地址，从而更改通过更改形参而更改实参的值。
<a name="J3cP3"></a>
### 案例3：传递引用类型参数2
```java
public class Person {
    private String name;
   // 省略构造函数、Getter&Setter方法
}

public static void main(String[] args) {
    Person Zhangsan = new Person("张三");
    Person Lisi = new Person("李四");
    swap(Zhangsan, Lisi);
    System.out.println("Zhangsan:" + Zhangsan.getName());
    System.out.println("Lisi:" + Lisi.getName());
}

public static void swap(Person person1, Person person2) {
    Person temp = person1;
    person1 = person2;
    person2 = temp;
    System.out.println("person1:" + person1.getName());
    System.out.println("person2:" + person2.getName());
}
```
输出：
```
person1:李四
person2:张三
Zhangsan:张三
Lisi:李四
```
解析：可以看到很奇怪！为什么形参的互换了却并没有改变实参的值？？！<br />但是其实！swap方法中的两个形参只是**copy了实参的地址**而已！在方法中只是交换了两个地址，所以实参并没有受到影响。
<a name="zn90o"></a>
## 引用传递是怎么样的？
Java中只有值传递，但是，引用传递到底是怎么样的呢？<br />看下面的C++实例：
```cpp
#include <iostream>

void incr(int& num)
{
    std::cout << "incr before: " << num << "\n";
    num++;
    std::cout << "incr after: " << num << "\n";
}

int main()
{
    int age = 10;
    std::cout << "invoke before: " << age << "\n";
    incr(*age);
    std::cout << "invoke after: " << age << "\n";
}
```
输出：
```
invoke before: 10
incr before: 10
incr after: 11
invoke after: 11
```
解析：可以看到，在incr函数中对形参的而修改，可以影响到实参的值。但是注意，这里使用的 int&<br />如果说使用 int 还是值传递！
<a name="HwefA"></a>
## 为什么Java不引入引用传递呢？
个人理解：<br />1、出于安全考虑，引用传递可以更改内部的值，这是及其不安全的。<br />2、Java设计初衷就是设计一门相对来说比较简单的语言，因此也就省略了许多不必要的东西。
<a name="yYLXR"></a>
## 总结

- 如果参数是基本类型的话，很简单，传递的就是基本类型的字面量值的拷贝，会创建副本。
- 如果参数是引用类型的话，传递的就是实参所引用的对象在堆中地址值的拷贝，同样会创建副本。
