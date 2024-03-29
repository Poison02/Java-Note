package cdu.zch.rpc.core.proxy.jdk;

import cdu.zch.rpc.core.common.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static cdu.zch.rpc.core.common.cache.CommonClientCache.*;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class JDKClientInvocationHandler implements InvocationHandler {

    private static final Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setArgs(args);
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setTargetServiceName(clazz.getName());
        // 这里面注入了一个uuid，对每一次的请求都做单独区分
        rpcInvocation.setUuid(UUID.randomUUID().toString());
        RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        // 这里就是将请求的参数放入到发送队列中
        SEND_QUEUE.add(rpcInvocation);
        long beginTime = System.currentTimeMillis();
        // 客户端请求超时的一个判断依据
        while (System.currentTimeMillis() - beginTime < 3 * 1000) {
            Object object = RESP_MAP.get(rpcInvocation.getUuid());
            if (object instanceof RpcInvocation) {
                return ((RpcInvocation) object).getResponse();
            }
        }
        throw new TimeoutException("client wait server's response timeout!");
    }
}
