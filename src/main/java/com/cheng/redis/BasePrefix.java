package com.cheng.redis;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 18:07 2018/6/27
 * @Reference:
 */
public abstract class BasePrefix implements Prefix{
    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        //this.prefix = prefix;
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {//0代表永不过期

        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();

        return className + ":" + prefix;
    }
}