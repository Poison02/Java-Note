package cdu.zch.bridge.remotes;

/**
 * 所有远程控制器的通用接口
 * @author Zch
 * @date 2023/7/30
 **/
public interface Remote {

    void power();

    void volumeDown();

    void volumeUp();

    void channelDown();

    void channelUp();

}
