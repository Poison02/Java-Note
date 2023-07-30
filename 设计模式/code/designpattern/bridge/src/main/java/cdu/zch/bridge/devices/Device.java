package cdu.zch.bridge.devices;

/**
 * 所有设备的通用接口
 * @author Zch
 * @date 2023/7/30
 **/
public interface Device {

    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int percent);

    int getChannel();

    void setChannel(int channel);

    void printStatus();

}
