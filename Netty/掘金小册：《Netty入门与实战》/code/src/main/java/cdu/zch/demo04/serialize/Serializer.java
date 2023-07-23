package cdu.zch.demo04.serialize;

import cdu.zch.demo04.serialize.impl.JSONSerializer;

/**
 * @author Zch
 * @date 2023/7/22
 **/
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
