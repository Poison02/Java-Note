package cdu.zch.abstractfactory.buttons;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class MacOSButton implements Button{
    @Override
    public void paint() {
        System.out.println("You have created MacOSButton!");
    }
}
