package cdu.zch.rpc.core.server;

import cdu.zch.rpc.interfaces.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class DataServiceImpl implements DataService {
    @Override
    public String sendData(String body) {
        System.out.println("已收到的参数长度：" + body.length());
        return "success";
    }

    @Override
    public List<String> getList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("idea1");
        arrayList.add("idea2");
        arrayList.add("idea3");
        return arrayList;
    }
}
