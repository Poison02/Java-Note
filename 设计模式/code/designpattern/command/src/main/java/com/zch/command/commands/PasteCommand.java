package com.zch.command.commands;

import com.zch.command.editor.Editor;

/**
 * 从粘贴板粘贴文字
 * @author Zch
 * @date 2023/8/6
 **/
public class PasteCommand extends Command {

    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        if (editor.clipboard == null || editor.clipboard.isEmpty()) return false;

        backup();
        editor.textField.insert(editor.clipboard, editor.textField.getCaretPosition());
        return true;
    }
}
