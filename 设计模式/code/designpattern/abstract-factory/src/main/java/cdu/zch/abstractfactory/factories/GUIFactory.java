package cdu.zch.abstractfactory.factories;

import cdu.zch.abstractfactory.buttons.Button;
import cdu.zch.abstractfactory.checkboxes.Checkbox;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public interface GUIFactory {

    Button createButton();
    Checkbox createCheckbox();

}
