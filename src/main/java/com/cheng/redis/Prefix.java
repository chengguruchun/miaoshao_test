package com.cheng.redis;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 18:06 2018/6/27
 * @Reference:
 */
public interface Prefix {
    public int expireSeconds();
    public String getPrefix();

}