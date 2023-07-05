package cdu.zch.thread.communication;

/**
 * @author Zch
 * @date 2023/7/5
 **/
class PrintNumber3 implements Runnable {

    private int number = 1;

    @Override
    public void run() {
        while (true) {
            // 加锁
            synchronized (this) {
                // 唤醒被wait那个线程
                this.notify();
                if (number <= 100) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;

                    // 线程执行此方法则进入阻塞状态，同时释放锁
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }

        }
    }
}

public class PrintNumberTest3 {

    public static void main(String[] args) {
        Runnable printNumber3 = new PrintNumber3();
        Thread t1 = new Thread(printNumber3, "线程1");
        Thread t2 = new Thread(printNumber3, "线程2");

        t1.start();
        t2.start();
    }

}
