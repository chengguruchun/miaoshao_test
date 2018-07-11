package com.cheng.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 16:08 2018/6/27
 * @Reference:
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(Prefix prefix, String key, Class<T> tClass) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, tClass);
            return t;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> boolean set(Prefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null) {
                return false;
            }else{
                String realKey = prefix.getPrefix() + key;
                int seconds = prefix.expireSeconds();
                if (seconds <= 0) {
                    jedis.set(realKey, str);

                }else {
                    jedis.setex(realKey, seconds, str);
                }
                return true;
            }
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> Boolean exists(Prefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            //生成真正的key
            String realKey = prefix.getPrefix() + key;

            return jedis.exists(realKey);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public <T> Long incr(Prefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;

            return jedis.incr(realKey);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public <T> long decr(Prefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;

            return jedis.incr(realKey);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static  <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static  <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public boolean delete(Prefix prefix, String s) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKEY = prefix.getPrefix() + s;
            long ret = jedis.del(realKEY);
            return ret > 0;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}