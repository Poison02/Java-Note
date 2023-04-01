import java.util.concurrent.*;

/**
 * @author Zch
 **/
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // 模拟计算需要1s
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws Exception{
        // 使用
        ExecutorService executor = Executors.newCachedThreadPool();
        CallableDemo demo = new CallableDemo();
        Future<Integer> result = executor.submit(demo);
        // 注意调用get方法会阻塞当前线程，知道得到结果
        // 所以实际编码中建议使用可以设置超时时间的重载get方法
        System.out.println(result.get());

        // 下面是使用FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executor.submit(futureTask);
        System.out.println(futureTask.get());
    }
}

class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 3;
    }
}
