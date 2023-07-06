package cdu.zch.rpc.core.proxy.javassist;

import cdu.zch.rpc.core.proxy.ProxyFactory;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class JavassistProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class clazz) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                clazz, new JavassistInvocationHandler(clazz));
    }
}
