package com.zch.observer.listeners;

import java.io.File;

/**
 * 收到通知后发送邮件
 * @author Zch
 * @date 2023/8/10
 **/
public class EmailNotificationListener implements EventListener {
    private String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
