<a name="iqQ7A"></a>
# 事务
事务：要么同时成功，要么同时失败，原子性！<br />**Redis单条命令是保证原子性的，但是Redis的事务是不保证原子性的！**<br />事务本质：**一组命令的集合。一个事务中的所有命令都会被序列化，在执行过程中会按照顺序执行。**<br />**一次性、顺序性、排他性。**执行一些命令。<br />**Redis事务没有隔离级别的概念！**<br />所有命令在事务中并没有直接被执行，只有在发起命令的时候才会执行。Exec<br />Redis的事务：

- 开启事务（multi）
- 命令入队（...）
- 执行事务（exec）

（锁：Redis可以实现乐观锁，watch）
> 正常执行事务！（exec）

```shell
127.0.0.1:6379[2]> multi # 开启事务
OK
127.0.0.1:6379[2](TX)> set k1 v1 # 命令入队
QUEUED
127.0.0.1:6379[2](TX)> set k2 v2
QUEUED
127.0.0.1:6379[2](TX)> get k2
QUEUED
127.0.0.1:6379[2](TX)> set k3 v3
QUEUED
127.0.0.1:6379[2](TX)> exec # 执行事务
1) OK
2) OK
3) "v2"
4) OK

```
> 放弃事务！（discard）

```shell
127.0.0.1:6379[2]> multi # 开启事务
OK
127.0.0.1:6379[2](TX)> set k1 v1
QUEUED
127.0.0.1:6379[2](TX)> set k2 v2
QUEUED
127.0.0.1:6379[2](TX)> set k4 v4
QUEUED
127.0.0.1:6379[2](TX)> discard # 取消事务
OK
127.0.0.1:6379[2]> get k4
(nil)
```
> 编译型异常（代码问题！命令有错！），事务中所有命令都不会被执行

```shell
127.0.0.1:6379[2]> multi
OK
127.0.0.1:6379[2](TX)> set k1 v1
QUEUED
127.0.0.1:6379[2](TX)> set k2 v2
QUEUED
127.0.0.1:6379[2](TX)> set v3 k3
QUEUED
127.0.0.1:6379[2](TX)> getset k3 # 错误的命令
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379[2](TX)> set k4 v4
QUEUED
127.0.0.1:6379[2](TX)> set k5 v5
QUEUED
127.0.0.1:6379[2](TX)> exec # 执行事务报错！
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379[2]> get k5 # 所有命令不会执行
(nil)
```
> 运行时异常，事务队列中存在语法性错误，那么执行命令中其他命令可以正常执行的，错误命令抛出异常	

```shell
127.0.0.1:6379[2]> set k1 v1
OK
127.0.0.1:6379[2]> multi
OK
127.0.0.1:6379[2](TX)> incr k1 # 这里逻辑是有问题的，但不会报错！
QUEUED
127.0.0.1:6379[2](TX)> set k2 v2
QUEUED
127.0.0.1:6379[2](TX)> get k2
QUEUED
127.0.0.1:6379[2](TX)> exec # 抛出异常
1) (error) ERR value is not an integer or out of range
2) OK
3) "v2"
127.0.0.1:6379[2]> get k1
"v1"
```
<a name="avU4v"></a>
# 监控（watch）
<a name="jISta"></a>
## 悲观锁
很悲观，认为什么时候都会出问题，无论做什么都会加锁
<a name="xkT2X"></a>
## 乐观锁
很乐观，认为什么时候都不会出问题，做什么都不会加锁！更新数据的时候去判断一下，在此期间是否有人修改过这个数据。<br />获取version，更新的时候比较version
```shell
127.0.0.1:6379[2]> set money 100
OK
127.0.0.1:6379[2]> set out 0
OK
127.0.0.1:6379[2]> watch money # 监视money
OK
127.0.0.1:6379[2]> multi 
OK
127.0.0.1:6379[2](TX)> decrby money 20
QUEUED
127.0.0.1:6379[2](TX)> incrby out 20
QUEUED
127.0.0.1:6379[2](TX)> exec # 事务正常结束，数据期间没有发生任何错误，执行成功！
1) (integer) 80
2) (integer) 20
```
下面演示多开窗口（多线程）
```shell
127.0.0.1:6379[2]> watch money
OK
127.0.0.1:6379[2]> multi
OK
127.0.0.1:6379[2](TX)> decrby money 10
QUEUED
127.0.0.1:6379[2](TX)> incrby out 10
QUEUED
127.0.0.1:6379[2](TX)> exec # 因为线程2修改了money，所以事务执行失败！
(nil)
```
```shell
127.0.0.1:6379[2]> get money
"80"
127.0.0.1:6379[2]> incrby money 100
(integer) 180
```
测试多线程修改值，使用watch可以当作Redis的乐观锁操作。<br />解决此问题：只需要`unwatch`退出监视，重新监视即可！
```shell
127.0.0.1:6379[2]> unwatch # 解锁
OK
127.0.0.1:6379[2]> watch money # 再次获取锁
OK
127.0.0.1:6379[2]> multi
OK
127.0.0.1:6379[2](TX)> decrby money 10
QUEUED
127.0.0.1:6379[2](TX)> incrby out 10
QUEUED
127.0.0.1:6379[2](TX)> exec
1) (integer) 170
2) (integer) 30
```
