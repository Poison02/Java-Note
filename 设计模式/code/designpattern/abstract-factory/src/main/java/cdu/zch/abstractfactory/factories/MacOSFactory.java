package cdu.zch.abstractfactory.factories;

import cdu.zch.abstractfactory.buttons.Button;
import cdu.zch.abstractfactory.buttons.MacOSButton;
import cdu.zch.abstractfactory.checkboxes.Checkbox;
import cdu.zch.abstractfactory.checkboxes.WindowsCheckbox;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class MacOSFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
