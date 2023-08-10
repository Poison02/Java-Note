package com.zch.observer.listeners;

import java.io.File;

/**
 * 收到通知后在日志中记录一条消息
 * @author Zch
 * @date 2023/8/10
 **/
public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
