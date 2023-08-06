package com.zch.command.commands;

import java.util.Stack;

/**
 * 命令历史
 * @author Zch
 * @date 2023/8/6
 **/
public class CommandHistory {
    private Stack<Command> history = new Stack<>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }

    public boolean isEmpty() { return history.isEmpty(); }
}
