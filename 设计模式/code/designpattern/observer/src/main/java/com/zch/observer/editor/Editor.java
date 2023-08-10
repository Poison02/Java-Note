package com.zch.observer.editor;

import com.zch.observer.publisher.EventManager;

import java.io.File;

/**
 * 具体发布者
 * @author Zch
 * @date 2023/8/10
 **/
public class Editor {

    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager("open", "save");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }

}
