import redis.clients.jedis.Jedis;

/**
 * Created by Xunyuc on 2017/7/15.
 */
public class AnyTest2 {
    public static void main(String args[]) {
        Jedis jedis = new Jedis("192.168.80.130", 6379);

        jedis.set("test","testtest");

        System.out.println(jedis.exists("test"));
    }


}


