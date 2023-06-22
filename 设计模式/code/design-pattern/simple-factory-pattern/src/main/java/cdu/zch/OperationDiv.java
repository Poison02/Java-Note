package cdu.zch;

/**
 * 减法类
 * @author Zch
 * @data 2023/6/22
 **/
public class OperationDiv extends Operation{
    @Override
    public double getResult() {
        return numberA - numberB;
    }
}
