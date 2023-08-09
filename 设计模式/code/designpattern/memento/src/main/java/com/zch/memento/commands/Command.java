package com.zch.memento.commands;

/**
 * 基础命令类
 * @author Zch
 * @date 2023/8/9
 **/
public interface Command {
    String getName();
    void execute();
}
