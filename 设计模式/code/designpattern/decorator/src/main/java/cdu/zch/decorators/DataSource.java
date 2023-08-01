package cdu.zch.decorators;

/**
 * 定义了读取和写入操作的通用数据接口
 * @author Zch
 * @date 2023/8/1
 **/
public interface DataSource {

    void writeData(String data);

    String readData();

}
