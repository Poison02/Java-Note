/**
 * @author Zch
 **/
public class RunnableDemo{

    public static class MyThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable.run");
        }
    }

    public static void main(String[] args) {


        new Thread(new MyThread()).start();

        // Java8函数式编程，Lambda表达式
        new Thread(() -> {
            System.out.println("Java8匿名内部类");
        }).start();
    }
}
