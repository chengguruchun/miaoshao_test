package com.cheng.redis;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 18:11 2018/6/27
 * @Reference:
 */
public class OrderKey extends BasePrefix{

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}