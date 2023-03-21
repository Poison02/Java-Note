package stack.collectionstack;

import java.util.Stack;

/**
 * 
 * 该类使用Java标准库的 Stack 测试栈的基本功能
 * 
 */
public class MyStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        // 入栈
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("此时栈中的有效元素个数为：" + stack.size()); // 5
        System.out.println("获取栈顶元素为：" + stack.peek()); // 5
        
        // 出栈
        stack.pop();

        System.out.println("获取栈顶元素为：" + stack.peek()); // 4
        System.out.println("此时栈中的有效元素个数为：" + stack.size()); // 4
        System.out.println("栈是否为空？" + stack.isEmpty()); // false
    }
}
