package cdu.zch;

/**
 * 除法类
 * @author Zch
 * @data 2023/6/22
 **/
public class OperationSub extends Operation{
    @Override
    public double getResult() {
        return numberA / numberB;
    }
}
