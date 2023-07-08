package cdu.zch.abstractfactory.app;

import cdu.zch.abstractfactory.buttons.Button;
import cdu.zch.abstractfactory.checkboxes.Checkbox;
import cdu.zch.abstractfactory.factories.GUIFactory;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class Application {

    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}
