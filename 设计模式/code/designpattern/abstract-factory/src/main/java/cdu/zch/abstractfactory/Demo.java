package cdu.zch.abstractfactory;

import cdu.zch.abstractfactory.app.Application;
import cdu.zch.abstractfactory.factories.GUIFactory;
import cdu.zch.abstractfactory.factories.MacOSFactory;
import cdu.zch.abstractfactory.factories.WindowsFactory;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class Demo {

    public static void main(String[] args) {
        Application app = configureApplication();
        app.paint();
    }

    static Application configureApplication() {
        Application app;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new MacOSFactory();
        } else {
            factory = new WindowsFactory();
        }
        app = new Application(factory);
        return app;
    }

}
