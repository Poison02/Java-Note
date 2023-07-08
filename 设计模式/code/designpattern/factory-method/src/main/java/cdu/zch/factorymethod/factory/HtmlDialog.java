package cdu.zch.factorymethod.factory;

import cdu.zch.factorymethod.buttons.Button;
import cdu.zch.factorymethod.buttons.HtmlButton;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class HtmlDialog extends Dialog{
    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
