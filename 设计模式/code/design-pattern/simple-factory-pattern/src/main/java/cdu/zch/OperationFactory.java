package cdu.zch;

/**
 * 运算工厂
 * @author Zch
 * @data 2023/6/22
 **/
public class OperationFactory {

    public static Operation createOperation(char operator) {
        Operation operation = null;
        switch (operator) {
            case '+':
                operation = new OperationAdd();
                break;
            case '-':
                operation = new OperationDiv();
                break;
            case '*':
                operation = new OperationMul();
                break;
            case '/':
                operation = new OperationSub();
                break;
        }
        return operation;
    }

}
