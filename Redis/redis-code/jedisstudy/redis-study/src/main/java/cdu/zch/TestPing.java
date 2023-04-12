package cdu.zch;

import redis.clients.jedis.Jedis;

/**
 * @author Zch
 **/
public class TestPing {

    public static void main(String[] args) {
        // 这里测试Redis连接，并发送Ping命令
        Jedis jedis = new Jedis("127.0.0.1", 6379);

//        System.out.println(jedis.ping());

//        System.out.println(jedis.keys("*"));

        jedis.select(2);
        System.out.println(jedis.keys("*"));

        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");

        System.out.println(jedis.mget("k1", "k2", "k3"));

        jedis.close();
    }

}
