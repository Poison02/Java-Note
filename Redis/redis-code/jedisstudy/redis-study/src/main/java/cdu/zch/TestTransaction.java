package cdu.zch;

import com.alibaba.fastjson2.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author Zch
 **/
public class TestTransaction {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "zch");
        String user1 = jsonObject.toJSONString();
        String user2 = jsonObject.toJSONString();

        // 开启事务
        jedis.select(2);
        Transaction multi = jedis.multi();
        try {
            multi.set("user1", user1);
            multi.set("user2", user2);
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }

    }

}
