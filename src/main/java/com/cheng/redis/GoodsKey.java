package com.cheng.redis;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 14:32 2018/7/8
 * @Reference:
 */
public class GoodsKey extends BasePrefix{

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
}