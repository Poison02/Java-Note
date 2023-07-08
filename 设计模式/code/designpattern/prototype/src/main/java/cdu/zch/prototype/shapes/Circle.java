package cdu.zch.prototype.shapes;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public class Circle extends Shape{

    public int radius;

    public Circle() {}

    public Circle(Circle target) {
        super(target);
        if (target != null) {
            this.radius = target.radius;
        }
    }

    @Override
    public Shape clone() {
        return new Circle();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Circle) || !super.equals(obj)) {
            return false;
        }
        Circle circle = (Circle) obj;
        return circle.radius == radius;
    }
}
