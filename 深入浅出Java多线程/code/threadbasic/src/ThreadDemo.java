/**
 * @author Zch
 **/
public class ThreadDemo {

    public static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread.run");
        }
    }

    public static void main(String[] args) {
        Thread myThread = new MyThread();

        myThread.start();
    }
}
