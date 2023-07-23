package cdu.zch.demo04.attribute;

import io.netty.util.AttributeKey;

/**
 * @author Zch
 * @date 2023/7/22
 **/
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
