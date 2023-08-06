package com.zch.command.commands;

import com.zch.command.editor.Editor;

/**
 * 将所选文字复制进粘贴板
 * @author Zch
 * @date 2023/8/6
 **/
public class CopyCommand extends Command {

    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        editor.clipboard = editor.textField.getSelectedText();
        return false;
    }
}
