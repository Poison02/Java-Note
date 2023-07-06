package cdu.zch.rpc.core.common.cache;

import cdu.zch.rpc.core.common.RpcInvocation;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class CommonClientCache {

    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue<>(100);
    public static Map<String, Object> RESP_MAP = new ConcurrentHashMap<>();

}
