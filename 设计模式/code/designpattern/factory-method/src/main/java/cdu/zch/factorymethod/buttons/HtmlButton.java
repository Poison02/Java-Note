package cdu.zch.factorymethod.buttons;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class HtmlButton implements Button{


    @Override
    public void render() {
        System.out.println("<button>Test Button</button>");
        onClick();
    }

    @Override
    public void onClick() {
        System.out.println("Click! Button says - 'Hello World!'");
    }
}
