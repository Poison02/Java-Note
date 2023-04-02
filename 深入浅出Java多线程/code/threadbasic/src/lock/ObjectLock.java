package lock;

/**
 * @author Zch
 **/
public class ObjectLock {
    private static Object lock = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("ThreadA " + i);
                }
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("ThreadB " + i);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Thread()).start();
        Thread.sleep(1000);
        new Thread(new ThreadB()).start();
    }
}
