package com.zch.shapes;

import com.zch.visitor.Visitor;

/**
 * @author Zch
 * @date 2023/8/14
 **/
public interface Shape {
    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
}
