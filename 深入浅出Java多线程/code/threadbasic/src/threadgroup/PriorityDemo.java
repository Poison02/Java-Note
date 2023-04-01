package threadgroup;

/**
 * @author Zch
 **/
public class PriorityDemo {

    public static void main(String[] args) {
        Thread a = new Thread();
        System.out.println("我是默认的优先级： " + a.getPriority());
        Thread b = new Thread();
        b.setPriority(10);
        System.out.println("我是修改之后的优先级： " + b.getPriority());

    }

}
