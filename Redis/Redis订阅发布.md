![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681636447387-74e8bb72-ab10-4c9d-8027-8e08c310d506.png#averageHue=%23f8eedb&clientId=u0188105e-0547-4&from=paste&height=726&id=u47932912&name=image.png&originHeight=726&originWidth=1342&originalType=binary&ratio=1&rotation=0&showTitle=false&size=338440&status=done&style=none&taskId=ub6b8f2b0-6b2f-4913-9876-cc3fb779032&title=&width=1342)<br />第一个：消息发送者，第二个：频道，第三个：消息订阅者<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681636611381-79d9e380-21f7-4142-abdc-bf63f91a3a02.png#averageHue=%23f7f6f4&clientId=u0188105e-0547-4&from=paste&height=916&id=uaaaf4a7e&name=image.png&originHeight=916&originWidth=1126&originalType=binary&ratio=1&rotation=0&showTitle=false&size=282540&status=done&style=none&taskId=uc8029181-38bf-4246-823b-1c54ccc6f4d&title=&width=1126)
> 测试

订阅端：
```shell
127.0.0.1:6379[2]> subscribe poison02 # 订阅一个频道poison02
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "poison02"
3) (integer) 1
1) "message" # 消息
2) "poison02" # 哪个频道
3) "Hello, Poison02!" # 消息内容
1) "message"
2) "poison02"
3) "hhhhh"
```
发送端：
```shell
127.0.0.1:6379[2]> publish poison02 "Hello, Poison02!" # 往指定频道发送消息
(integer) 1
127.0.0.1:6379[2]> publish poison02 "hhhhh"
(integer) 1
```
> 原理

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681637262663-4c2f474b-868f-455f-9ccb-8abc08b15486.png#averageHue=%23d4d0c8&clientId=u0188105e-0547-4&from=paste&height=404&id=u574d93c8&name=image.png&originHeight=404&originWidth=1359&originalType=binary&ratio=1&rotation=0&showTitle=false&size=446507&status=done&style=none&taskId=u1d2e812f-96b9-416d-bed0-4ca79eb3a08&title=&width=1359)<br />使用场景：

1. 实时消息系统！
2. 实时聊天（频道当作聊天室，将信息回显给所有人）
3. 订阅、关注系统

稍微复杂的场景就会使用消息中间件做。
