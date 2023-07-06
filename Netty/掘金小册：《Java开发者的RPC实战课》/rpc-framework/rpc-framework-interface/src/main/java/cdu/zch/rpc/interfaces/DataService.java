package cdu.zch.rpc.interfaces;

import java.util.List;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public interface DataService {

    /**
     * 发送数据
     * @param body 消息体
     * @return String
     */
    String sendData(String body);

    /**
     * 获取数据
     * @return List
     */
    List<String> getList();

}
