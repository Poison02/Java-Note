package com.zch.components;

import com.zch.mediator.Mediator;

/**
 * @author Zch
 * @date 2023/8/8
 **/
public interface Component {

    void setMediator(Mediator mediator);
    String getName();

}
