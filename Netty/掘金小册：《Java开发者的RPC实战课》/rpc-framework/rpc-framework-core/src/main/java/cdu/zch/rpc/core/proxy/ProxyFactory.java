package cdu.zch.rpc.core.proxy;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public interface ProxyFactory {

    <T> T getProxy(final Class clazz) throws Throwable;

}
