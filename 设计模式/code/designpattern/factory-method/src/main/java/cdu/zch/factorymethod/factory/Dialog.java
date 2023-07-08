package cdu.zch.factorymethod.factory;

import cdu.zch.factorymethod.buttons.Button;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public abstract class Dialog {

    public void renderWindow() {
        Button okButton = createButton();
        okButton.render();
    }

    public abstract Button createButton();

}
