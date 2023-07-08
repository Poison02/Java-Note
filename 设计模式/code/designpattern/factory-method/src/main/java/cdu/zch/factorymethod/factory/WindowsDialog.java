package cdu.zch.factorymethod.factory;

import cdu.zch.factorymethod.buttons.Button;
import cdu.zch.factorymethod.buttons.WindowsButton;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class WindowsDialog extends Dialog{
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}
