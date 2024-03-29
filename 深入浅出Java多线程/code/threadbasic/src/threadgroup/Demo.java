package threadgroup;

/**
 * @author Zch
 **/
public class Demo {

    public static void main(String[] args) {
        Thread testThread = new Thread(() -> {
            System.out.println("testThread当前线程组名字：" +
                    Thread.currentThread().getThreadGroup().getName());
            System.out.println("testThread线程名字" +
                    Thread.currentThread().getName());
        });

        testThread.start();
        System.out.println("执行main所在线程的线程组名字：" +
                Thread.currentThread().getThreadGroup().getName());
        System.out.println("执行main方法的线程名字：" +
                Thread.currentThread().getName());
    }

}
