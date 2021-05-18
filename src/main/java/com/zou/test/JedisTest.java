package com.zou.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author: 邹祥发
 * @date: 2021/5/16 13:28
 * redis API测试
 */
public class JedisTest {
    public static void main(String[] args) {
        //创建redis对象
        Jedis jedis = new Jedis("192.168.210.128", 6379);
        //测试连接
        String value = jedis.ping();
        System.out.println(value);

        //key测试
        jedis.set("k1", "zou1");
        jedis.set("k2", "zou2");
        jedis.set("k3", "zou");
        Set<String> keys = jedis.keys("*");
        System.out.println("key的数量：" + keys.size());
        for (String key : keys) {
            System.out.print(key + "\t");
        }
        System.out.println("\n" + "是否存在k2：" + jedis.exists("k2"));
        /*
         ttl:当key不存在时，返回-2。
         当key存在但没有设置剩余生存时间时，返回-1。否则，以秒为单位，返回key的剩余生存时间。
        */
        System.out.println(jedis.ttl("k2"));
        System.out.println("k3的值：" + jedis.get("k3"));

        //String测试
        jedis.mset("str1", "k1", "str2", "k2", "str3", "k3");
        System.out.println(jedis.mget("str1", "str2", "str3"));

        //List测试
        jedis.lpush("myList", "one", "two", "three");
        List<String> list = jedis.lrange("myList", 0, -1);
        System.out.println("遍历list集合：");
        for (String element : list) {
            System.out.print(element + "\t");
        }

        //set测试
        jedis.sadd("orders", "order1");
        jedis.sadd("orders", "order2");
        jedis.sadd("orders", "order3");
        jedis.sadd("orders", "order4");
        //redis中的srem的返回值：被成功移除的元素的数量，不包括被忽略的元素。
        System.out.println("\n" + "orders中被移除的元素的数量：" + jedis.srem("orders", "order2"));
        Set<String> orders = jedis.smembers("orders");
        System.out.println("遍历set集合orders中的元素：");
        for (String order : orders) {
            System.out.print(order + "\t");
        }

        //hash测试
        jedis.hset("hash", "userName", "zou");
        System.out.println("\n" + "hash的中userName值：" + jedis.hget("hash", "userName"));
        HashMap<String, String> map = new HashMap<>();
        map.put("telephone", "17886977918");
        map.put("address", "衡阳");
        map.put("email", "1565453341@qq.com");
        jedis.hmset("hash1", map);
        List<String> result = jedis.hmget("hash1", "telephone", "address", "email");
        System.out.println("hash1中的信息：");
        for (String element : result) {
            System.out.print(element + "\t");
        }

        //zset测试
        jedis.zadd("zset01", 100d, "z3");
        jedis.zadd("zset01", 90d, "l4");
        jedis.zadd("zset01", 80d, "w5");
        jedis.zadd("zset01", 70d, "z6");
        Set<String> zset01 = jedis.zrange("zset01", 0, -1);
        System.out.println("\n" + "zset01中的信息：");
        for (String e : zset01) {
            System.out.print(e + "\t");
        }


        //关闭连接
        jedis.close();
    }
}
