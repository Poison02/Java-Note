package com.zch;

import com.zch.components.*;
import com.zch.mediator.Editor;
import com.zch.mediator.Mediator;

import javax.swing.*;

/**
 * @author Zch
 * @date 2023/8/8
 **/
public class Demo {

    public static void main(String[] args) {
        Mediator mediator = new Editor();

        mediator.registerComponent(new Title());
        mediator.registerComponent(new TextBox());
        mediator.registerComponent(new AddButton());
        mediator.registerComponent(new DeleteButton());
        mediator.registerComponent(new SaveButton());
        mediator.registerComponent(new List(new DefaultListModel()));
        mediator.registerComponent(new Filter());

        mediator.createGUI();
    }

}
