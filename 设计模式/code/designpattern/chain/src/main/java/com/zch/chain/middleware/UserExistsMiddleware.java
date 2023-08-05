package com.zch.chain.middleware;

import com.zch.chain.server.Server;

/**
 * 检查用户登录信息
 * @author Zch
 * @date 2023/8/5
 **/
public class UserExistsMiddleware extends Middleware{
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    @Override
    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}
