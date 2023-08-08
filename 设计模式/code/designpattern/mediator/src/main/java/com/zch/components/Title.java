package com.zch.components;

import com.zch.mediator.Mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * @author Zch
 * @date 2023/8/8
 **/
public class Title extends JTextField implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void processComponentKeyEvent(KeyEvent keyEvent) {
        mediator.markNote();
    }

    @Override
    public String getName() {
        return "Title";
    }
}
