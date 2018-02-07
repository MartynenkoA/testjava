package com.example.demo;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

public class JedisFactory {
    private static JedisPool jedisPool;
    private static JedisFactory instance = null;

    public JedisFactory() throws URISyntaxException {
        URI redisURI = new URI("redis://h:p4a95ce00460b82905244664bbef4107eef3f3edee81f1d50fc7a95f39bce98b7@ec2-34-252-77-108.eu-west-1.compute.amazonaws.com:41919");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        jedisPool = new JedisPool(poolConfig, redisURI);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public static JedisFactory getInstance() throws URISyntaxException {
        if (instance == null) {
            instance = new JedisFactory();
        }
        return instance;
    }
}
