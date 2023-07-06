package cdu.zch.rpc.core.common.cache;

import cdu.zch.rpc.core.registry.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class CommonServerCache {

    public static final Map<String, Object> PROVIDER_CLASS_MAP = new HashMap<>();

    public static final Set<URL> PROVIDER_URL_SET = new HashSet<>();

}
