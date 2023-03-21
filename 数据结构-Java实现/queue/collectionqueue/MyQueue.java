package queue.collectionqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * 该类使用Java自带的Queue接口中的LinkedList测试队列
 * 
 */

public class MyQueue {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        // 入队
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println("此时队列中的元素个数为：" + queue.size()); // 5
        System.out.println("获取队列头的元素：" + queue.peek()); // 1

        // 出队
        int data = queue.poll();
        System.out.println("出队列的元素为：" + data); // 1

        System.out.println("此时队列的元素为：" + queue.size()); // 4
        System.out.println("队列是否为空？" + queue.isEmpty()); // false

    }
}
