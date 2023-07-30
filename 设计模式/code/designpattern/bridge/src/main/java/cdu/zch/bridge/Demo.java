package cdu.zch.bridge;

import cdu.zch.bridge.devices.Device;
import cdu.zch.bridge.devices.Radio;
import cdu.zch.bridge.devices.Tv;
import cdu.zch.bridge.remotes.AdvancedRemote;
import cdu.zch.bridge.remotes.BasicRemote;

/**
 * @author Zch
 * @date 2023/7/30
 **/
public class Demo {

    public static void main(String[] args) {
        testDevice(new Tv());
        testDevice(new Radio());
    }

    public static void testDevice(Device device){
        System.out.println("Tests with basic remote");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }

}
