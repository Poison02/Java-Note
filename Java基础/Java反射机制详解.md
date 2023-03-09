<a name="ut7py"></a>
## 何为反射？
如果说研究过框架对的底层原理或者自己写过框架的话，一定不会陌生。<br />反射之所以被称为框架的灵魂，主要是因为它赋予了我么你在运行时分析类以及执行类中方法的能力。<br />通过反射你可以获取任意一个类的所有属性和方法，你还可以调用这些方法和属性。
<a name="NhObI"></a>
## 反射的应用场景了解么？
平时大部分都是在写业务代码，很少接触到直接使用反射机制的场景。<br />但是！并不代表反射没有用。相反，正是因为反射，你才能这么轻松的使用各种框架。如：Sring/Spring Boot，MyBatis等框架中都大量的使用了反射机制。<br />**这些框架中也大量使用了动态代理，而动态代理的实现也依赖反射。**<br />比如下面是通过JDK实现动态代理的实例代码，其中就使用了反射类`Method`来调用指定的方法。
```java
public class DebugInvocationHandler implements InvocationHandler {
    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("before method " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("after method " + method.getName());
        return result;
    }
}


```
另外，像Java中的一大利器 `注解` 的实现也用到了反射。<br />为什么你使用Spring的时候，一个`@Component`注解就声明了一个类为Spring Bean呢？为什么你通过一个`@Value`注解就读取到配置文件中的值呢？究竟是怎么起作用的呢？<br />这些都是应为你可以基于反射分析类，然后获取到类/属性/方法/方法的参数上的注解。你获取到注解之后，就可以做进一步的处理。
<a name="HKDCO"></a>
## 谈谈反射机制的优缺点
**优点：**可以让我们的代码更加零零或、为各种框架提供开箱即用的功能提供了便利。<br />**缺点：**让我们在运行时有了分析操作类的能力，这同样也增加了安全问题。比如可以无视泛型参数的安全检查（泛型参数的安全检查发生在编译时）。另外，反射的性能也要稍微差点，不过，对框架来说时影响不大的。
<a name="woz0O"></a>
## 反射实战
<a name="xe0Kz"></a>
### 获取Class对象的四种方式
如果我们动态获取到这些信息，我们需要依靠Class对象。Class类对象将一个类的方法、变量等信息告诉运行的而程序。Java提供了四种方式获取Class对象。
<a name="PZcHw"></a>
#### 1、知道具体类的情况下可以使用：
```java
Class alunbarClass = TargetObject.class;
```
但是我们一般是不知道具体类的，基本都是通过遍历包下面的类来获取Class对象，通过此方式获取Class对象不会进行初始化。
<a name="hj4Df"></a>
#### 2、通过`Class.forName()`传入类的全路径获取：
```java
Class alunbarClass = Class.forName("cn.cdu.TargetObject");
```
<a name="HdiAr"></a>
#### 3、通过兑现实例 `instancs.getClass()` 获取：
```java
TargetObject o = new TatgetObject();
Class alunbarCLass2 = o.getCLass();
```
<a name="Pz4hl"></a>
#### 4、通过类加载器`xxxClassLoader.loadClass()`传入类路径获取：
```java
ClassLoader.getSystemClassLoader().loadClass("cn.cdu.TargetObject");
```
通过类加载器获取Class对象不会进行初始化，意味着不进行包括初始化等一系列步骤，静态代码块和静态对象不会得到执行。
<a name="dI3MR"></a>
### 反射的一些基本操作
<a name="a3j1t"></a>
#### 1、创建一个我们要使用的反射操作的类 `TargetObject`：
```java
package cn.cdu;

public class TargetObject{
    private String value;

	public TargetObject(){
        value = "Java";
    }

	public void publicMethod(String s){
        System.out.println("I love " + s);
	}

	private void privateMethod(){
        System.out.println("value is " + value);
    }
}
```
<a name="cZ9qW"></a>
#### 2、使用反射操作这个类的方法以及参数
```java
package cn.cdu;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main(){
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
    	InstantiationException, InvocationTargetException, NoSuchFieldException{
        /**
    	*获取 TargetObject类的Class 对象并且创建TargetObject类实例
		*/
        Class<?> targetClass = Class.forName("cn.javaguide.TargetObject");
        TargetObject targetObject = (TargetObject) targetClass.newInstance();
        /**
         * 获取 TargetObject 类中定义的所有方法
         */
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        /**
         * 获取指定方法并调用
         */
        Method publicMethod = targetClass.getDeclaredMethod("publicMethod",
                String.class);

        publicMethod.invoke(targetObject, "JavaGuide");

        /**
         * 获取指定参数并对参数进行修改
         */
        Field field = targetClass.getDeclaredField("value");
        //为了对类中的参数进行修改我们取消安全检查
        field.setAccessible(true);
        field.set(targetObject, "JavaGuide");

        /**
         * 调用 private 方法
         */
        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        //为了调用private方法我们取消安全检查
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObject);
    }
}
        
```
输出内容：
```
publicMethod
privateMethod
I love JavaGuide
value is JavaGuide
```
<a name="e0Kk5"></a>
#### 可能出现的问题：
可能会出现`ClassNotFoundException`异常，具体原因是因为需要用自己创建的`TargetObject`包
```java
Class<?> ttargetObject = Class.forName("cn.cdu.TargetObject");
```
