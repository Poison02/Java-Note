<a name="gOfne"></a>
# RDB（Redis DataBase）
在主从复制中，rdb用于备用。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681476092541-7d018403-d069-4640-86c3-11f2e028844f.png#averageHue=%23fefefc&clientId=uf32d5951-8447-4&from=paste&height=760&id=u86559d10&name=image.png&originHeight=760&originWidth=1298&originalType=binary&ratio=1&rotation=0&showTitle=false&size=177774&status=done&style=none&taskId=u4449c42f-8560-432a-ab9c-a80bbd5e630&title=&width=1298)<br />Redis是内存数据库，如果不讲内存保存到硬盘中，那么一旦服务器进程退出，资源就全部都没有。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681475408318-71255649-3f14-467f-b0e2-583e331dfc3d.png#averageHue=%23e7e5df&clientId=uf32d5951-8447-4&from=paste&height=206&id=ubf22e460&name=image.png&originHeight=206&originWidth=1365&originalType=binary&ratio=1&rotation=0&showTitle=false&size=295983&status=done&style=none&taskId=u6ece399b-8682-44a7-ae61-7de36d144de&title=&width=1365)<br />默认的就是RDB，一般情况下不需要修改配置<br />保存的是`dump.rdb`文件，都在配置文件的快照中配置的<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681475524333-41230238-c9a2-4cbd-93b5-ceb76de766b5.png#averageHue=%23040604&clientId=uf32d5951-8447-4&from=paste&height=131&id=ubcf982fe&name=image.png&originHeight=131&originWidth=794&originalType=binary&ratio=1&rotation=0&showTitle=false&size=64283&status=done&style=none&taskId=u8f997631-1f5d-455a-945e-ad31fe92b51&title=&width=794)
> 触发机制

1. save的规则满足的情况下，会自动触发rdb规则
2. 执行flushall命令，也会触发我们的rdb规则
3. 退出redis，也会产生rdb文件！

备份就会自动生成一个rdb
> 如何恢复rdb文件

1. 只需要将rdb文件放在redis启动目录下就可以，启动的时候会自动检查rdb文件，回复其中的数据
2. 查看文件存在的位置

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681475946959-d5d24681-2d22-410e-9f61-d3235a9fc126.png#averageHue=%23f4f2f0&clientId=uf32d5951-8447-4&from=paste&height=105&id=u4699227d&name=image.png&originHeight=105&originWidth=990&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51977&status=done&style=none&taskId=u8334bfab-4f11-42c6-afbe-fc5b69135d8&title=&width=990)<br />优点：

1. 适合大规模的数据恢复
2. 对数据的完整性要求不高

缺点：

1. 需要一定的时间间隔进程操作，如果Redis意外宕机，最后一次修改数据就没了
2. fork进程的时候，会占用一定的内存空间
<a name="LB2XZ"></a>
# AOF（Append Only File）
将我们所有的命令都记录下来，history，恢复的时候将所有命令执行<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681476239496-1986b4b3-0033-4c7f-b959-726126fd94c8.png#averageHue=%23fefefd&clientId=uf32d5951-8447-4&from=paste&height=769&id=uc46daa37&name=image.png&originHeight=769&originWidth=1087&originalType=binary&ratio=1&rotation=0&showTitle=false&size=191159&status=done&style=none&taskId=u303197b2-50fe-4fc0-b075-880438ba50c&title=&width=1087)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681476247870-2c003c03-9a89-471a-89bc-8551600557b3.png#averageHue=%23d5d1c9&clientId=uf32d5951-8447-4&from=paste&height=124&id=u06347545&name=image.png&originHeight=124&originWidth=1375&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136125&status=done&style=none&taskId=ua9e3e406-4999-49ad-836a-194a084a870&title=&width=1375)<br />AOF保存的是`appendonly.aof`文件<br />在配置文件中配置<br />默认是不开启的，需要手动进行设置。（`appendonly yes`）<br />如果这个aof文件有错误，这时候Redis是不能启动的，我们需要通过`redis-check-aof`工具去修复。命令<br />`redis-check-aof --fix appendonly.aof`<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681476771375-2a35e47d-239d-44df-9334-62dcc528f6b2.png#averageHue=%23e4d8cb&clientId=uf32d5951-8447-4&from=paste&height=210&id=udbddbca2&name=image.png&originHeight=210&originWidth=1170&originalType=binary&ratio=1&rotation=0&showTitle=false&size=176823&status=done&style=none&taskId=u70ddf3a4-4f9f-4d9f-befe-0c18c7661c1&title=&width=1170)<br />优点：

1. 每一次修改都同步，文件的完整性会更加好
2. 每秒同步一次，可能会丢失一秒的数据
3. 从不同不，效率最高

缺点：

1. 相对于数据文件来说，aof远远大于rdb，修复的速度比rdb慢
2. aof运行效率也比rdb慢，所以我们redis默认的配置就是rdb持久化

扩展：<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681477084735-5a5694ba-a06c-4a1c-85ec-73d240fe3c61.png#averageHue=%23d7d3cb&clientId=uf32d5951-8447-4&from=paste&height=417&id=u541935b4&name=image.png&originHeight=417&originWidth=1342&originalType=binary&ratio=1&rotation=0&showTitle=false&size=446543&status=done&style=none&taskId=uc5fd010c-cee0-4d1e-82fd-e0c8f1a0da6&title=&width=1342)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681477107932-e3468a51-3f94-4021-b5a7-ca5641ecd98a.png#averageHue=%23d8d3cc&clientId=uf32d5951-8447-4&from=paste&height=407&id=u85e1debb&name=image.png&originHeight=407&originWidth=1350&originalType=binary&ratio=1&rotation=0&showTitle=false&size=428158&status=done&style=none&taskId=uaca22dae-7d6b-4512-9a3a-261f4d7fd00&title=&width=1350)

