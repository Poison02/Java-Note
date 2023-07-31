package cdu.zch.composite.shapes;

import java.awt.*;

/**
 * 通用形状接口
 * @author Zch
 * @date 2023/7/31
 **/
public interface Shape {

    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void move(int x, int y);
    boolean isInsideBounds(int x, int y);
    void select();
    void unSelect();
    boolean isSelected();
    void paint(Graphics graphics);

}
