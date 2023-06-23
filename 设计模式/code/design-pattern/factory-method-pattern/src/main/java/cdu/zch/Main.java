package cdu.zch;

/**
 * @author Zch
 * @data 2023/6/23
 **/
public class Main {
    public static void main(String[] args) {
        IFactory factory = new UndergraduateFactory();
        LeiFeng student = factory.createLeiFeng();
        student.buyRice();
        student.sweep();
        student.wash();

        LeiFeng volunteer = new VolunteerFactory().createLeiFeng();
        volunteer.buyRice();
        volunteer.sweep();
        volunteer.wash();
    }
}