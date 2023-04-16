![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645194014-b332aa3b-68cf-437e-926a-e387f6328e80.png#averageHue=%23cdc8c0&clientId=ud99d36ba-8381-4&from=paste&height=147&id=u28d86f43&name=image.png&originHeight=147&originWidth=1320&originalType=binary&ratio=1&rotation=0&showTitle=false&size=158428&status=done&style=none&taskId=uaa3133d7-7296-4f52-963a-f92df8c17f5&title=&width=1320)
<a name="hyMMF"></a>
# 缓存穿透（查不到）
> 概念

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645382040-bae18847-6223-4655-8a43-dadfb0b7fefc.png#averageHue=%23d4d0c8&clientId=ud99d36ba-8381-4&from=paste&height=119&id=u3af4773f&name=image.png&originHeight=119&originWidth=1361&originalType=binary&ratio=1&rotation=0&showTitle=false&size=158288&status=done&style=none&taskId=u3c51597d-a209-4404-825b-eb79c3689c9&title=&width=1361)
> 解决方案

<a name="QV2HK"></a>
## 布隆过滤器
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645447355-17debcd9-5a64-4af4-8e40-9ba72115c21e.png#averageHue=%23f9f9f9&clientId=ud99d36ba-8381-4&from=paste&height=646&id=u6ca73737&name=image.png&originHeight=646&originWidth=1328&originalType=binary&ratio=1&rotation=0&showTitle=false&size=168615&status=done&style=none&taskId=uedc50e05-dc70-4fa6-bdbb-1f9bf10d3c7&title=&width=1328)
<a name="Mvpf2"></a>
## 缓存空对象
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645490791-130aac09-5774-46bd-9a40-0d8ad0ec0d56.png#averageHue=%23fbfbfb&clientId=ud99d36ba-8381-4&from=paste&height=666&id=ufede6bde&name=image.png&originHeight=666&originWidth=1362&originalType=binary&ratio=1&rotation=0&showTitle=false&size=155504&status=done&style=none&taskId=u1a9f544e-d5bd-4e00-9fcc-b9aab15bb4b&title=&width=1362)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645510107-bd18f3bc-a805-45e7-bd36-4fca64c15f3d.png#averageHue=%23cbc4bc&clientId=ud99d36ba-8381-4&from=paste&height=202&id=ufe99773b&name=image.png&originHeight=202&originWidth=1347&originalType=binary&ratio=1&rotation=0&showTitle=false&size=157530&status=done&style=none&taskId=u67354b26-70ad-4ff7-9408-deffd8eb36a&title=&width=1347)
<a name="oLsOz"></a>
# 缓存击穿（量太大，缓存过期）
> 概念

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645530716-fb3279cf-f646-4ea3-bc4f-49edbf3684de.png#averageHue=%23cfcbc3&clientId=ud99d36ba-8381-4&from=paste&height=180&id=u5ba72085&name=image.png&originHeight=180&originWidth=1334&originalType=binary&ratio=1&rotation=0&showTitle=false&size=188343&status=done&style=none&taskId=u06ec9ef2-0578-4f1f-8f2e-28fbd95159c&title=&width=1334)
> 解决方案

<a name="XZY3W"></a>
## 设置热点数据永不过期
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645654419-352efd9b-47b7-4fbd-8a0e-31a8ca3090b8.png#averageHue=%23d9ddda&clientId=ud99d36ba-8381-4&from=paste&height=49&id=u70ec868a&name=image.png&originHeight=49&originWidth=840&originalType=binary&ratio=1&rotation=0&showTitle=false&size=39098&status=done&style=none&taskId=u65bf6932-37dc-47d1-8965-a2c49e092ce&title=&width=840)
<a name="jl5dF"></a>
## 加互斥锁
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645665838-bfe60a79-1ecd-403c-928a-248d2b961316.png#averageHue=%23e7e8e2&clientId=ud99d36ba-8381-4&from=paste&height=77&id=u9a4fd511&name=image.png&originHeight=77&originWidth=1333&originalType=binary&ratio=1&rotation=0&showTitle=false&size=124326&status=done&style=none&taskId=ube681557-d9de-48f8-b17a-c14282c0781&title=&width=1333)
<a name="MjvLP"></a>
# 缓存雪崩
> 概念

![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645785483-4c472246-b1bd-4d6d-ac10-e40791937b02.png#averageHue=%23cec9c0&clientId=ud99d36ba-8381-4&from=paste&height=208&id=uaa91ef9f&name=image.png&originHeight=208&originWidth=1378&originalType=binary&ratio=1&rotation=0&showTitle=false&size=198868&status=done&style=none&taskId=u40d2ed16-4f0b-47fe-b6d6-e217bb59240&title=&width=1378)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645844220-cb7c88a1-f543-40b9-b5b6-7ce9f3c6d6ba.png#averageHue=%23f1f0ec&clientId=ud99d36ba-8381-4&from=paste&height=573&id=ubd5a9040&name=image.png&originHeight=573&originWidth=932&originalType=binary&ratio=1&rotation=0&showTitle=false&size=179884&status=done&style=none&taskId=u2744aa76-112a-4ab6-b193-7a41d7b95bc&title=&width=932)
> 解决方案

<a name="RqVKu"></a>
## Redis高可用
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645908911-f5fab79e-56ec-4197-b2c9-f4208c57a807.png#averageHue=%23f8f7f7&clientId=ud99d36ba-8381-4&from=paste&height=99&id=u9e931b21&name=image.png&originHeight=99&originWidth=1345&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69597&status=done&style=none&taskId=u6c99d1de-826c-46fd-acec-9e47fe65505&title=&width=1345)
<a name="TmfVJ"></a>
## 限流降级
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645919535-46b801d4-5de1-4643-b7e9-38e444d4236c.png#averageHue=%23d7d4ce&clientId=ud99d36ba-8381-4&from=paste&height=83&id=ud36885a5&name=image.png&originHeight=83&originWidth=1311&originalType=binary&ratio=1&rotation=0&showTitle=false&size=95934&status=done&style=none&taskId=u6017a670-f141-4b29-8beb-f3b752f8820&title=&width=1311)
<a name="UtkLK"></a>
## 数据预热
![image.png](https://cdn.nlark.com/yuque/0/2023/png/35204765/1681645925864-790bb662-9870-4f8d-a8bf-b8ce2747189e.png#averageHue=%23e6e7e2&clientId=ud99d36ba-8381-4&from=paste&height=75&id=u26e177f9&name=image.png&originHeight=75&originWidth=1321&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136501&status=done&style=none&taskId=ueb0e678a-b051-4ab0-bba4-9e56773da4f&title=&width=1321)
