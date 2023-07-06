package cdu.zch.rpc.core.registry.zookeeper;

import cdu.zch.rpc.core.registry.RegistryService;
import cdu.zch.rpc.core.registry.URL;

import java.util.List;

import static cdu.zch.rpc.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;
import static cdu.zch.rpc.core.common.cache.CommonServerCache.PROVIDER_URL_SET;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public abstract class AbstractRegister implements RegistryService {

    @Override
    public void register(URL url) {
        PROVIDER_URL_SET.add(url);
    }

    @Override
    public void unRegister(URL url) {
        PROVIDER_URL_SET.remove(url);
    }

    @Override
    public void subscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.add(url.getServiceName());
    }

    @Override
    public void doUnSubscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.remove(url.getServiceName());
    }

    public abstract void doAfterSubscribe(URL url);
    public abstract void doBeforeSubscribe(URL url);
    public abstract List<String> getProviderIps(String serviceName);
}
