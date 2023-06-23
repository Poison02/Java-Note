package cdu.zch;

/**
 * @author Zch
 * @data 2023/6/23
 **/
public class VolunteerFactory implements IFactory{
    @Override
    public LeiFeng createLeiFeng() {
        return new Volunteer();
    }
}
