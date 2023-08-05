package com.zch.chain.middleware;

/**
 * 检查用户角色
 *
 * @author Zch
 * @date 2023/8/5
 **/
public class RoleCheckMiddleware extends Middleware {
    @Override
    public boolean check(String email, String password) {
        if ("admin@example.com".equals(email)) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(email, password);

    }
}
