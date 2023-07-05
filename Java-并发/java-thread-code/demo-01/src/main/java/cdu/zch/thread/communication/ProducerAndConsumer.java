package cdu.zch.thread.communication;

/**
 * @author Zch
 * @date 2023/7/5
 **/
class Clerk {

    private int productNum = 0;

    public synchronized void addProduct() {
        if (productNum >= 20) {
            try {
                // 等待
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            productNum++;
            System.out.println(Thread.currentThread().getName() + "生产者生产第" + productNum + "个产品");
            // 唤醒所有wait
            notifyAll();
        }

    }

    public synchronized void subProduct() {
        if (productNum <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "消费者消费第" + productNum + "个产品");
            productNum--;
            notifyAll();
        }
    }

}


class Producer implements Runnable{

    Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            // 生产者生产产品
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.addProduct();
        }
    }
}


class Consumer implements Runnable{

    Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            // 消费者消费产品
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.subProduct();
        }
    }
}


public class ProducerAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        Thread pro1 = new Thread(producer, "生产者1");
        Thread con1 = new Thread(consumer, "消费者1");
        Thread con2 = new Thread(consumer, "消费者2");

        pro1.start();
        con1.start();
        con2.start();
    }

}
