package com.zch.state;

import com.zch.state.ui.Player;
import com.zch.state.ui.UI;

/**
 * @author Zch
 * @date 2023/8/11
 **/
public class Demo {

    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }

}
