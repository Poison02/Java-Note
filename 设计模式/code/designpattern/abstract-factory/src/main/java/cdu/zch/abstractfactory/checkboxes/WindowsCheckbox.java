package cdu.zch.abstractfactory.checkboxes;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class WindowsCheckbox implements Checkbox{
    @Override
    public void paint() {
        System.out.println("You have created WindowsCheckbox!");
    }
}
