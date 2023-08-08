package com.zch.components;

import com.zch.mediator.Mediator;
import com.zch.mediator.Note;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Zch
 * @date 2023/8/8
 **/
public class AddButton extends JButton implements Component {
    private Mediator mediator;

    public AddButton() {
        super("Add");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.addNewNote(new Note());
    }

    @Override
    public String getName() {
        return "AddButton";
    }
}
