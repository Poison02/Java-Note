package cdu.zch.thread.create;

/**
 * @author Zch
 * @date 2023/7/4
 **/

// 方式一 继承Thread类
class EvenNumber extends Thread{
    @Override
    public void run() {
        for (int i = 1; i <= 100; i ++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
class OddNumber extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i ++) {
            if (i % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
public class PrintNumberTest {

    public static void main(String[] args) {
        // 方式1，调用继承Thread的类
        Thread t1 = new EvenNumber();
        t1.start();

        Thread t2 = new OddNumber();
        t2.start();

        // 方式2，使用匿名类
        new Thread(() -> {
            for (int i = 1; i <= 100; i ++) {
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 1; i <= 100; i ++) {
                if (i % 2 != 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        }).start();
    }

}
