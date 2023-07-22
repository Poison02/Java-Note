package cdu.zch.demo02.serialize.impl;

import cdu.zch.demo02.serialize.Serializer;
import cdu.zch.demo02.serialize.SerializerAlogrithm;
import com.alibaba.fastjson.JSON;

/**
 * @author Zch
 * @date 2023/7/22
 **/
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
