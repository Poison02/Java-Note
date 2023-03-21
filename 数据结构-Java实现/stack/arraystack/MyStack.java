package stack.arraystack;

import java.util.Arrays;

/**
 * 
 * 这里是使用数组实现栈，且使用泛型
 * 
 */
public class MyStack<T> {
    private T[] arr; // 存放元素的数组

    private int size; // 栈的元素个数

    public MyStack() {
        this(12); // 最大长度
    }

    @SuppressWarnings("unchecked")
    public MyStack(int maxSize) {
        this.arr = (T[]) new Object[maxSize]; // 初始化栈
    }

    // 获取栈的长度
    public int getSize() {
        return this.size;
    }
    
    // 栈判空
    public boolean isEmpty() {
        return this.size == 0;
    }

    // 返回栈顶元素
    public T peek() {
        // 判断栈是否为空，为空则抛出异常 栈为空 StackEmptyException
        if (isEmpty()) {
            throw new StackEmptyException("该栈为空！不能返回任何元素！");
        }

        return this.arr[this.size - 1];
    }

    // 入栈
    public T push(T data) {
        // 判断栈是否满，若满了就增加空间
        if (this.size == arr.length) {
            T[] newArr;
            newArr = Arrays.copyOf(this.arr, this.arr.length * 2);
            this.arr = newArr;
        }

        // 没有满就将元素入栈
        this.arr[size] = data;
        // 长度 + 1
        this.size++;
        // 返回添加的元素
        return data;
    }

    // 出栈
    public T pop() {
        if (isEmpty()) {
            throw new StackEmptyException("该栈为空！不能出栈！");
        }

        // 获得栈顶元素 后面返回
        T data = this.arr[this.size - 1];
        this.size --;
        return data;
    }

}
