package cdu.zch.decorators;

/**
 * 抽象基础装饰
 * @author Zch
 * @date 2023/8/1
 **/
public class DataSourceDecorator implements DataSource{

    private DataSource wrapper;

    public DataSourceDecorator(DataSource source) {
        this.wrapper = source;
    }

    @Override
    public void writeData(String data) {
        wrapper.writeData(data);
    }

    @Override
    public String readData() {
        return wrapper.readData();
    }
}
