package cdu.zch;

/**
 * 客户端测试类
 * @author Zch
 * @data 2023/6/22
 **/
public class Main {

    public static void main(String[] args) {
        Operation operation;
        char[] operator = new char[] {'+', '-', '*', '/'};

        for (char c: operator) {
            operation = OperationFactory.createOperation(c);
            operation.numberA = 1;
            operation.numberB = 2;
            System.out.println(operation.getResult());
        }
    }

}
