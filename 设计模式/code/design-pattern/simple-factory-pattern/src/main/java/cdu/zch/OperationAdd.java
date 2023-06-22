package cdu.zch;

/**
 * 加法类
 * @author Zch
 * @data 2023/6/22
 **/
public class OperationAdd extends Operation{
    @Override
    public double getResult() {
        return numberA + numberB;
    }
}
