package cdu.zch.rpc.core.client;

import cdu.zch.rpc.core.proxy.ProxyFactory;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class RpcReference {

    public ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    /**
     * 根据接口类型获取代理对象
     * @param tClass
     * @return
     * @param <T>
     * @throws Throwable
     */
    public <T> T get(Class<T> tClass) throws Throwable {
        return proxyFactory.getProxy(tClass);
    }
}
