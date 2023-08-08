package com.zch.components;

import com.zch.mediator.Mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Zch
 * @date 2023/8/8
 **/
public class DeleteButton extends JButton implements Component {
    private Mediator mediator;

    public DeleteButton() {
        super("Del");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.deleteNote();
    }

    @Override
    public String getName() {
        return "DelButton";
    }
}
