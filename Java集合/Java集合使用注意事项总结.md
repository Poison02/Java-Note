<a name="Ur5HR"></a>
# 集合判空
《阿里巴巴Java开发手册》的描述如下：<br />**判断所有集合内部的元素是否为空，使用**`isEmpty()`**方法，而不是**`size() == 0`**的方式。**<br />这是因为`isEmpty()`方法色可读性更好，并且时间复杂度为O(1)。<br />绝大部分我们使用的集合的`size()`方法的时间复杂度也是O(1)，不过，也有很多时间复杂度不是O(1)的，比如`java.util.concurrent`包下的某些集合`ConcurrentLinkedQueue`、`ConcurrentHashMap`...<br />下面是`ConcurrentHashMap`的`size()`方法和`isEmpty()`方法的源码。
```java
public int size(){
    long n = sumCount();
    return ((n < 0L) ? 0 :
            (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE : 
            (int)n);
}

final long sumCount(){
    CounterCell[] as = counterCells;
    CounterCell a;
    long sum = baseCount;
    if (as != null){
        for (int i = 0; i < as.length; ++i){
            if ((a = as[i]) != null){
                sum += a.value;
            }
        }
    }
    return sum;
}
public boolean isEmpty(){
    return sumCount() <= 0L;
```
<a name="Dj5Vi"></a>
# 集合转Map
《阿里巴巴Java开发手册》的描述如下：<br />**在使用**`java.util.stream.Collectors`**类的**`toMap()`**方法转为**`Map`**集合时，一定要注意当value为null时会抛NPE异常**
```java
class Person{
    private String name;
    private String phoneNumber;
    // getter and setter
}

List<Person> bookList = new ArrayList<>();
bookList.add(new Person("jack", "1111");
bookList.add(new Person("tom", null);
// 空指针异常
bookList.stream().collect(Collectors.toMap(person::getName, Person::getPhoneNumber));
```
解释：<br />`java.util.stream.Collectors`的`toMap()`方法内部调用了`Map`接口的`merge()`方法。<br />`merge()`方法会先调用`Objects.requireNonNull()`方法判断value是否为空。
```java
public static <T> T requireNonNull(T obj){
    if (null == obj){
        throw new NullPointerException();
    }
    return obj;
}
```
<a name="ddZKw"></a>
# 集合遍历
《阿里巴巴Java开发手册》的描述如下：<br />**不要再**`foreach`**循环里进行元素的**`remove/add`**操作。remove元素请使用**`Iterator`**方式，如果并发操作，需要对**`Iterator`**对象加锁。**<br />通过反编译你会发现`foreach`语法底层其实还是依赖`Iterator`。不过，`remove/add`操作直接调用的是集合自己的方法，而不是`Iterator`的`remove/add`方法。<br />这就导致了`Iterator`莫名其妙的发现自己铀元素被`remove/add`，然后，它就会抛出一个`ConcurrentModificationException`来提示用户发生了并发修改异常。这就是单线程状态下产生的 **fail-fast 机制。**<br />java8开始，可以使用`Collection#removeIf()`方法删除满足特定条件的元素，如
```java
List<Integer> list = new ArrayList<>();
for (int i = 0; i <= 10; i++{
    list.add(i);
}
list.removeIf(filter -> filter % 2 == 0); // 删除list中的所有偶数
System.out.println(list); // [1,3,5,7,9]
```
除了使用`Iterator`进行遍历之外，还可以：

- 使用普通的for循环
- 使用fail-safe的集合类，`java.util`包下面的所有的集合类都是`fail-fast`的，而`java.util.concurrent`包下面的所有的类都是`fail-save`的。

...
<a name="Y1TYI"></a>
# 集合去重
《阿里巴巴Java开发手册》的描述如下：<br />**可以利用**`Set`**元素唯一的特性，可以快速对一个集合进行去重操作，避免使用**`List`**的**`contains()`**进行遍历去重或者判断包含操作。**<br />这里用`HashSet`和`ArrayList`为例说明：
```java
// Set 去重代码
public static <T> Set<T> removeDuplicateBySet(List<T> data) {

	if (CollectionUtils.isEmpty(data)){
        return new HashSet<>();
    }
    return new HashSet<>(data);
}

// List 去重代码
public static <T> List<T> removeDuplicateBySet(List<T> data) {

	if (CollectionUtils.isEmpty(data)){
        return new ArrayList<>();
    }
    List<T> result = new ArrayList<>(data.size());
    for (T current : data){
        if (! result.contains(current)){
            result.add(current);
        }
    }
    return result;
}
```
两者核心差别在于`contains()`方法的实现。<br />`HashSet`的`contains()`底部依赖的`HashMap`的`containsKey()`方法，时间复杂度接近于O(1)（没有出现哈希冲突的时候为O(1)）。
```java
private transient HashMap<E, Object> map;
public boolean contains(Object o){
    return map.containsKey(o);
}
```
我们有N个元素插入进Set中，时间复杂度就接近是O（n）。<br />`ArrayList`的`contains()`方法是通过遍历所有元素的方法来做的，时间复杂度接近是O（n）。
```java
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}
public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}

```
我们的`List`有N个元素，那时间复杂度就接近是O(n^2)。
<a name="CuD15"></a>
# 集合转数组
《阿里巴巴Java开发手册》的描述如下：<br />**使用集合转数组的方法，必须使用集合的**`**toArray(T[] array)**`**，传入的是类型完全一致，长度为0的空数组。**<br />`**toArray(T[] array)**`**方法的参数是一个泛型数组，如果**`**toArray**`**方法中没有传递任何参数的话返回的是**`**Object**`**类型数组。**
```java
String[] s = new String[]{
    "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
};
List<String> list = Arrays.asList(s);
Collections.reverse(list);
// 没指定类型就会报错
s = list.toArray(new String[0]);
```
由于JVM的优化，`new String[0]`作为`Collection.toArray()`方法的参数现在使用很好，`new String[0]`就是起一个模板的作用，指定了返回数组的类型，0是为了节省空间，一位内他只是为了说明返回的类型。
<a name="B2US9"></a>
# 数组转集合
《阿里巴巴Java开发手册》的描述如下：<br />**使用工具类**`Arrays.asList()`**把数组转换成集合时，不能使用其修改集合相关的方法，它的**`add/remove/clear`**方法会抛出**`UnsupportedOperationException`**异常。**<br />`Array.asList()`在平时开发中还是比较常见的，我们可以使用它将一个数组转换成一个`List`集合。
```java
String[] myArray = {"Apple", "Banana", "Orange"};
List<String> myList = Arrays.asList(myArray);
// 上面的两个语句等于下面这个
List<String> myList = Arrays.asList("Apple","Banana", "Orange");
```
JDK源码对于这个方法的说明：
```java
/**
	*返回由指定数组支持的固定大小的列表，此方法作为基于数组和基于集合的API之间的桥梁
	*与Collection.toArray()结合使用，返回的List是可序列化并实现RandomAccess接口
*/
public static <T> List<T> asList(T... a){
    return new ArrayList<>(a);
}
```
<a name="pwhEf"></a>
## 使用注意事项：
<a name="LARH2"></a>
### `Arrays.asList()`是泛型方法，传递的数组必须是对象数组，而不是基本类型。
```java
int[] myArray = {1, 2, 3};
List myList = Arrays.asList(myArray);
System.out.println(myList.size());// 1
System.out.println(myList.get(0)); // 数组地址值
System.out.println(myList.get(1)); // 报错：ArrayIndexOutOfBoundException
int[] array = (int[]) myList.get(0); 
System.out.println(array[0]); // 1
```
当传入一个原生数据类型数组时，`Arrays.asList()`的真正得到的参数就不是数组中的元素，而是数组对象本身！此时`List`的唯一元素就是这个数组，这也就解释了上面的代码。<br />我们可以使用包装数据类型解决：
```java
Integer[] myArray = {!, 2, 3};
```
<a name="IDUJq"></a>
### 使用集合的修改方法：`add()`、`remove()`、`clear()`会抛出异常
```java
List myList = Arrays.asList(1, 2, 3);
myList.add(4); // 运行报错，UnsupportOperationException
myList.remove(1); // 运行报错，UnsupportOperationException
myList.clear(); // 运行报错，UnsupportOperationException
```
`Arrays.asList()`方法返回的并不是`java.util.ArrayList`，而是`java.util.Arrays`的一个内部类，这个内部类并没有实现集合的修改方法或者说并没有重写这些方法。
```java
List myList = Arrays.asList(1, 2, 3);
System.out.println(myList.getClass()); // class java.util.Arrays$ArrayList
```
<a name="zvRWM"></a>
### 那我们如何正确将数组转换成`ArrayList`？
<a name="wBl2B"></a>
#### 手动实现工具类
```java
static <T> List<T> arratToList(final T{} array){
    final List<T> l = new ArrayList<T>(array.length);

	for (final T s: array){
        l.add(s);
    }
    return l;
}

Integer[] myArray = {1, 2, 3};
System.out.println(arrayToList(myArray).getClass()); // class java.util.ArrayList
```
<a name="GzOoO"></a>
#### 最简便的方法
```java
List list = new ArrayList<>(Arrays.asList("a", "b", "c");
```
<a name="nW8FA"></a>
#### 使用java8的`Stream`（推荐）
```java
Integer[] myArray = {1, 2, 3};
List myList = Arrays.stream(myArray).collect(Collectors.toList());
// 基本类型也可以实现转换（依赖boxed的装箱操作）
int[] myArray2 = {1, 2, 3};
List myList = Arrays.stream(myArray2).boxed().collect(Collectors.toList());
```
<a name="CLT7e"></a>
#### 使用Guava
对于不可变集合，你可以使用`ImmutableList`类及其`of()`与`copyOf()`工厂方法：（参数不能为空）
```java
List<String> l1 = ImmutableList.of("String", "elements"); // from varargs
List<String> l2 = ImmutableList.copyOf(aStringArray); // from array
```
对于可变集合，你可以使用`Lists`类及其`newArrayList()`工厂方法：
```java
List<String> l1 = Lists.newArrayList(anotherListOrCollection); // from collection
List<String> l2 = Lists.newArrayList(aStringArray); // from array
List<String> l3 = Lists.newArrayList("or", "string", "elements"); // from varargs
```
<a name="Oesfm"></a>
#### 使用`Apache Commons Collections`
```java
List<String> list = new ArrayList<String>();
CollectionUtils.addAll(list, str);
```
<a name="JSlyl"></a>
#### 使用 Java9 的`List.of()`方法
```java
Integer[] array = {1, 2, 3};
List<Integer> list = List.of(array);
```
