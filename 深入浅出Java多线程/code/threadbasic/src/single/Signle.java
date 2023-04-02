package single;

/**
 * @author Zch
 **/
public class Signle {
    private static volatile int single = 0;

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            while (single < 5) {
                if (single % 2 == 0) {
                    System.out.println("ThreadA " + single);
                    single++;
                }
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            while (single < 5) {
                if (single % 2 == 1) {
                    System.out.println("ThreadB " + single);
                    single++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(10);
        new Thread(new ThreadB()).start();
    }
}
