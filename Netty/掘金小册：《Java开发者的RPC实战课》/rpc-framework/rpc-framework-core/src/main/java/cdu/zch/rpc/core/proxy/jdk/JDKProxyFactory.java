package cdu.zch.rpc.core.proxy.jdk;

import cdu.zch.rpc.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class clazz) throws Throwable {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}
        , new JDKClientInvocationHandler(clazz));
    }
}
