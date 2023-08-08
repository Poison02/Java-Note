package com.zch.mediator;

import com.zch.components.Component;

import javax.swing.*;

/**
 * 定义通用的中介者接口
 * @author Zch
 * @date 2023/8/8
 **/
public interface Mediator {

    void addNewNote(Note note);
    void deleteNote();
    void getInfoFromList(Note note);
    void saveChanges();
    void markNote();
    void clear();
    void sendToFilter(ListModel listModel);
    void setElementsList(ListModel list);
    void registerComponent(Component component);
    void hideElements(boolean flag);
    void createGUI();

}
