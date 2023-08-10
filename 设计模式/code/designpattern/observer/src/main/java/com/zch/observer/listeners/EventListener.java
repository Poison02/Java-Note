package com.zch.observer.listeners;

import java.io.File;

/**
 * 通用观察者接口
 * @author Zch
 * @date 2023/8/10
 **/
public interface EventListener {

    void update(String eventType, File file);

}
