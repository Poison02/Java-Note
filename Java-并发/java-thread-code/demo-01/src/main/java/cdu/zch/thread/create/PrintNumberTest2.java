package cdu.zch.thread.create;

/**
 * @author Zch
 * @date 2023/7/4
 **/
// 方式2，实现Runnable接口
class EvenNumber2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
public class PrintNumberTest2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new EvenNumber2());
        t1.start();

        new Thread(() ->
                System.out.println(Thread.currentThread().getName() + "hahaha")
        ).start();
    }

}
