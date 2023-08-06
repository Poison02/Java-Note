package com.zch.command.commands;

import com.zch.command.editor.Editor;

/**
 * 抽象基础命令
 * @author Zch
 * @date 2023/8/6
 **/
public abstract class Command {
    public Editor editor;
    private String backup;

    Command(Editor editor) {
        this.editor = editor;
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract boolean execute();
}
