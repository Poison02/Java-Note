![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681637595275-a8cdedd7-4bb5-4c26-a3ca-75d7ac79676e.png#averageHue=%23d8d5ce&clientId=u6299da8f-038c-4&from=paste&height=488&id=udc06b2f4&name=image.png&originHeight=488&originWidth=1351&originalType=binary&ratio=1&rotation=0&showTitle=false&size=504587&status=done&style=none&taskId=ud4ea2534-b7c5-466d-9373-6acf38bce5e&title=&width=1351)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681637623926-d4ab8af7-11aa-489a-b39f-b2c4777784fe.png#averageHue=%23d7d2ca&clientId=u6299da8f-038c-4&from=paste&height=179&id=u918a1c20&name=image.png&originHeight=179&originWidth=1315&originalType=binary&ratio=1&rotation=0&showTitle=false&size=184219&status=done&style=none&taskId=u68ddbdc7-ae47-41e2-8627-0c6dda530af&title=&width=1315)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681637629278-1b4b74c8-9c13-4ace-9435-61ce61cada01.png#averageHue=%23f7f3f2&clientId=u6299da8f-038c-4&from=paste&height=669&id=u4e03db8b&name=image.png&originHeight=669&originWidth=943&originalType=binary&ratio=1&rotation=0&showTitle=false&size=224301&status=done&style=none&taskId=ud1e84286-c18b-45d6-abf4-01851b24235&title=&width=943)<br />主从复制，读写分离。大部分情况下都是在进行读操作，减缓服务器的压力！<br />建议工程项目中搭建集群<br />环境配置时只需要配置从库，不需要配置主库。
```shell
127.0.0.1:6379> info replication # 查看信息
# Replication
role:master
connected_slaves:0
master_failover_state:no-failover
master_replid:7b86a149689c6fdfecf9e214c9ce855838f61f19
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
默认情况下，每台Redis服务器都是主节点<br />配置主机从机，只需要将从机指向主机
```shell
127.0.0.1:6380> slaveof 127.0.0.1 6379 # 将主机指向6379端口
OK
127.0.0.1:6380> info replication
# Replication
role:slave # 当前角色：从机
master_host:127.0.0.1 # 主机信息
master_port:6379
master_link_status:up
master_last_io_seconds_ago:0
master_sync_in_progress:0
slave_read_repl_offset:14
slave_repl_offset:14
slave_priority:100
slave_read_only:1
replica_announced:1
connected_slaves:0
master_failover_state:no-failover
master_replid:26740eee8273b57c5a600a64908da693135cb4f8
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:14
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:15
repl_backlog_histlen:0

```
```shell
127.0.0.1:6381> slaveof 127.0.0.1 6379
OK
127.0.0.1:6381> info replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6379
master_link_status:down
master_last_io_seconds_ago:-1
master_sync_in_progress:0
slave_read_repl_offset:0
slave_repl_offset:0
master_link_down_since_seconds:-1
slave_priority:100
slave_read_only:1
replica_announced:1
connected_slaves:0
master_failover_state:no-failover
master_replid:3eabdc768f7cafe3ac09cf46a920566881fa954f
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
主机：
```shell
127.0.0.1:6379> info replication
# Replication
role:master # 角色：主机
connected_slaves:2 # 两个从机
slave0:ip=127.0.0.1,port=6380,state=online,offset=154,lag=0 # 从机1
slave1:ip=127.0.0.1,port=6381,state=online,offset=154,lag=1 # 从机2
master_failover_state:no-failover
master_replid:26740eee8273b57c5a600a64908da693135cb4f8
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:154
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:154
```
真实的配置应该在配置文件中配置，是永久的！现在用命令这是临时的！
> 细节

主机可以写，从机不能写只能读！主机中所有信息和数据都会被从机保存<br />测试：主机断开连接，从机依旧连接到主机，但没有写操作，若主机又回来了，从机依旧得到主机信息。<br />若使用命令行配置的主从关系，这时候如果重启了从机，则从机就会变成主机。只要变成从机，立马就会从主机中获取。
> 复制原理

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681642881218-241375f5-15c1-4dbb-8834-e1d1a6e0e229.png#averageHue=%23eaeae8&clientId=u6299da8f-038c-4&from=paste&height=291&id=ufaffad93&name=image.png&originHeight=291&originWidth=1336&originalType=binary&ratio=1&rotation=0&showTitle=false&size=216095&status=done&style=none&taskId=uffb8cbd1-f13c-4a47-a70c-a0b5aee0d78&title=&width=1336)
> 层层链路

上一个M连接下一个S<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681643149692-81bbbfb6-1a31-406a-93e6-af9e59d792eb.png#averageHue=%23f1f1f1&clientId=u6299da8f-038c-4&from=paste&height=362&id=u40c9c277&name=image.png&originHeight=362&originWidth=1266&originalType=binary&ratio=1&rotation=0&showTitle=false&size=64002&status=done&style=none&taskId=u70daba52-2678-4b3a-9e0e-7dfa80fc256&title=&width=1266)<br />这时候也可以完成我们的主从复制！<br />如果主机断开了，我们可以使用 `slaveof no one`让自己变成主机！

