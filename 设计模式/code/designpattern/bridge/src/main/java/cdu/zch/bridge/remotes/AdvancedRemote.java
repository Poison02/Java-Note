package cdu.zch.bridge.remotes;

import cdu.zch.bridge.devices.Device;

/**
 * 高级远程控制器
 * @author Zch
 * @date 2023/7/30
 **/
public class AdvancedRemote extends BasicRemote{

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
