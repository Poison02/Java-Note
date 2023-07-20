package cdu.zch.spi;

/**
 * @author Zch
 * @date 2023/7/20
 **/
public class LoggerBack implements Logger{
    @Override
    public void info(String s) {
        System.out.println("Logback info 打印日志：" + s);
    }

    @Override
    public void debug(String s) {
        System.out.println("Logback debug 打印日志：" + s);
    }
}
