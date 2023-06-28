package com.azha.Utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool pool;
    static{
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(10);//最大连接数
        config.setMaxIdle(5);//最大空闲连接数
        config.setMaxWaitMillis(100);//最大等待时间毫秒
        config.setBlockWhenExhausted(false);//连接耗尽时不堵塞
        pool=new JedisPool("192.168.112.128",6379);//创建连接池
    }
    public static Jedis getJedis(){
        Jedis jedis=pool.getResource();
        jedis.auth("123456");//记得登录
        return jedis;
    }
}
