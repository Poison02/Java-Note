package com.zch.flyweight.forest;

import com.zch.flyweight.trees.Tree;
import com.zch.flyweight.trees.TreeFactory;
import com.zch.flyweight.trees.TreeType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 绘制的森林
 * @author Zch
 * @date 2023/8/3
 **/
public class Forest extends JFrame {

    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, Color color, String otherTreeData) {
        TreeType type = TreeFactory.getTreeType(name, color, otherTreeData);
        Tree tree = new Tree(x, y, type);
        trees.add(tree);
    }

    @Override
    public void paint(Graphics graphics) {
        for (Tree tree : trees) {
            tree.draw(graphics);
        }
    }

}
