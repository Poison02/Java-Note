package com.zch.flyweight.trees;

import java.awt.*;

/**
 * 包含每棵树的独特状态
 * @author Zch
 * @date 2023/8/3
 **/
public class Tree {

    private int x;
    private int y;
    private TreeType type;

    public Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}
