package cdu.zch.builder.components;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class GPSNavigator {

    private String route;

    public GPSNavigator() {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }

    public GPSNavigator(String manualRoute) {
        this.route = manualRoute;
    }

    public String getRoute() {
        return route;
    }

}
