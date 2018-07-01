package com.cheng.redis;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 18:10 2018/6/27
 * @Reference:
 */
public class UserKey extends BasePrefix{
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

}