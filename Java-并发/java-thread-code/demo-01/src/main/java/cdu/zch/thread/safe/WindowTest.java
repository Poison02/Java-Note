package cdu.zch.thread.safe;

/**
 * @author Zch
 * @date 2023/7/4
 **/

class Window implements Runnable {

    int ticket = 100;

    @Override
    public void run() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName() + ":售票:" + ticket);
                        ticket--;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    break;
                }
            }
        }

    }
}

public class WindowTest {

    public static void main(String[] args) {
        Runnable t = new Window();

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }

}
