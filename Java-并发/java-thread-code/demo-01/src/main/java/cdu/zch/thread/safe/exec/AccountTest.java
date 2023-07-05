package cdu.zch.thread.safe.exec;

/**
 * 银行有一个账户。
 * 有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打印账户余额。
 * 问题：该程序是否有安全问题，如果有，如何解决？
 * @author Zch
 * @date 2023/7/4
 **/
class Account {
    private double balance;

    public synchronized void deposit(double amt) {
        if (amt > 0) {
            balance += amt;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "存钱1000，余额为：" + balance);
    }
}
class Customer implements Runnable {

    private Account account;

    public Customer(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.deposit(1000);
        }
    }
}
public class AccountTest {

    public static void main(String[] args) {
        Account account = new Account();
        Runnable customer = new Customer(account);

        Thread t1 = new Thread(customer);
        Thread t2 = new Thread(customer);

        t1.start();
        t2.start();
    }

}
