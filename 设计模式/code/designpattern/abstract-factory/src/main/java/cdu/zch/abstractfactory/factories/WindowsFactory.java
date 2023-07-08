package cdu.zch.abstractfactory.factories;

import cdu.zch.abstractfactory.buttons.Button;
import cdu.zch.abstractfactory.buttons.WindowsButton;
import cdu.zch.abstractfactory.checkboxes.Checkbox;
import cdu.zch.abstractfactory.checkboxes.WindowsCheckbox;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class WindowsFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
