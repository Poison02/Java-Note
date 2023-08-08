package com.zch.mediator;

/**
 * 笔记夹
 * @author Zch
 * @date 2023/8/8
 **/
public class Note {

    private String name;
    private String text;

    public Note() {
        name = "New note";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return name;
    }

}
