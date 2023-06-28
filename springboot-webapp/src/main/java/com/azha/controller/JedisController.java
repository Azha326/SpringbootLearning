package com.azha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashMap;

@RestController
@RequestMapping("/jedis")
public class JedisController {

    @GetMapping("/tryRedis")
    public String tryRedis(){
        Jedis jedis=new Jedis("192.168.112.128",6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        System.out.println(jedis.set("name","张三"));
        System.out.println(jedis.get("name"));

        return jedis.get("name");
    }
    @GetMapping("/tryTransaction")
    public String tryTransaction(){//试试事务
        Jedis jedis=new Jedis("192.168.112.128",6379);
        jedis.auth("123456");
        Transaction t1=jedis.multi();
        System.out.println(t1.set("name","李四"));
//        System.out.println(t1.discard());
        System.out.println(t1.exec());
        return jedis.get("name");
    }
    @GetMapping("/tryJedisPool")
    public String tryJedisPool(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(10);//最大连接数
        config.setMaxIdle(5);//最大空闲连接数
        config.setMaxWaitMillis(100);//最大等待时间毫秒
        config.setBlockWhenExhausted(false);//连接耗尽时不堵塞
        JedisPool pool=new JedisPool("192.168.112.128",6379);//创建连接池
        Jedis jedis=pool.getResource();//从连接池中获取链接
        jedis.auth("123456");//记得登录
        System.out.println(jedis.get("name"));

        jedis.lpush("arr1","右进1");
        jedis.rpush("arr1","左进1");

        jedis.hset("map1","hash1","value1");
        HashMap map=new HashMap();
        map.put("hash2","value2");
        jedis.hset("map1",map);

        jedis.sadd("set1","member1","member2","member3");

        jedis.zadd("zset1",10,"member1");
        jedis.zadd("zset1",20,"member2");
        jedis.zadd("zset1",30,"member3");
        pool.close();
        return "test over,check logs";
    }
}
