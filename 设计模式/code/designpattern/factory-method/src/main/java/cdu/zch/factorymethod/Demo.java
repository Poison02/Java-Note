package cdu.zch.factorymethod;

import cdu.zch.factorymethod.factory.Dialog;
import cdu.zch.factorymethod.factory.HtmlDialog;
import cdu.zch.factorymethod.factory.WindowsDialog;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class Demo {

    private static Dialog dialog;

    public static void main(String[] args) {
        configure();
        runBusinessLogic();
    }

    static void configure() {
        if (System.getProperty("os.name").equals("Windows 11")) {
            dialog = new WindowsDialog();
        } else {
            dialog = new HtmlDialog();
        }
    }

    static void runBusinessLogic() {
        dialog.renderWindow();
    }

}
