package com.zch.visitor;

import com.zch.shapes.Circle;
import com.zch.shapes.CompoundShape;
import com.zch.shapes.Dot;
import com.zch.shapes.Rectangle;

/**
 * @author Zch
 * @date 2023/8/14
 **/
public interface Visitor {
    String visitDot(Dot dot);

    String visitCircle(Circle circle);

    String visitRectangle(Rectangle rectangle);

    String visitCompoundGraphic(CompoundShape cg);
}
