package cdu.zch.singlethread;

/**
 * 单线程下的示例
 * @author Zch
 * @date 2023/7/8
 **/
public class Singleton {

    private static Singleton instance;
    public String value;

    private Singleton(String value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        if (instance == null) {
            instance = new Singleton(value);
        }
        return instance;
    }

}
