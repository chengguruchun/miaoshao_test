package com.cheng.util;

import java.util.UUID;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:59 2018/7/1
 * @Reference:
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
