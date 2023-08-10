package com.zch.observer;

import com.zch.observer.editor.Editor;
import com.zch.observer.listeners.EmailNotificationListener;
import com.zch.observer.listeners.LogOpenListener;

/**
 * @author Zch
 * @date 2023/8/10
 **/
public class Demo {

    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
