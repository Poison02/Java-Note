package queue.linkedqueue;

import java.util.Arrays;

/**
 * 
 * 该类是用来实现循环队列的，采用数组实现
 * 
 */

public class MyCircleQueue<T> {
    
    // 队列
    private T[] queue;
    // 头指针
    private int front; 
    // 尾指针
    private int rear;
    // 队列长度
    private int maxSize;
    private static final int DEFAULT_SIZE = 12;

    public MyCircleQueue() {
        this(DEFAULT_SIZE);
    }

    // 初始化
    @SuppressWarnings("unchecked")
    public MyCircleQueue(int maxSize) {
        queue = (T[]) new Object[maxSize];
        this.maxSize = maxSize;
        this.front = 0;
        this.rear = 0;
    }

    // 得到队列长度
    public int getSize() {
        return (this.rear - this.front + this.maxSize) % this.maxSize;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return this.rear == this.front;
    }

    // 判断队列是否满
    public boolean isFull() {
        return ((this.rear + 1) % this.maxSize) == this.front;
    }

    // 得到队首元素
    public T peek() {
        if (isEmpty()) {
            throw new QueueEmptyException("该队列为空！不能得到任何元素！");
        }
        return queue[this.front];
    }

    // 入队
    public void offer(T data) {
        // 判断队列是否满
        if (isFull()) {
            T[] newQueue;
            newQueue = Arrays.copyOf(this.queue, DEFAULT_SIZE * 2);
            this.queue = newQueue;
        }

        queue[this.rear] = data;
        // 向后迭代
        rear = (rear + 1) % this.maxSize;
    }

    // 出队
    public T poll() {
        // 判断队列是否为空
        if (isEmpty()) {
            throw new QueueEmptyException("该队列为空！不能出队！");
        }
        
        T data = queue[front];
        // 向后迭代
        front = (front + 1) % this.maxSize;
        return data;
    }


    public static void main(String[] args) {
        MyCircleQueue<Integer> queue = new MyCircleQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println("队列长度为：" + queue.getSize());

        Integer data = queue.poll();
        System.out.println("出队的元素为：" + data);
        System.out.println("此时队列的长度为：" + queue.getSize());
    }
}
