package com.zch.memento.history;

import com.zch.memento.editor.Editor;

/**
 * 备忘录
 * @author Zch
 * @date 2023/8/9
 **/
public class Memento {
    private String backup;
    private Editor editor;

    public Memento(Editor editor) {
        this.editor = editor;
        this.backup = editor.backup();
    }

    public void restore() {
        editor.restore(backup);
    }
}
