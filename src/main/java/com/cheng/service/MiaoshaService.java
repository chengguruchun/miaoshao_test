package com.cheng.service;

import com.cheng.dao.GoodsDao;
import com.cheng.domain.Goods;
import com.cheng.domain.MiaoshaOrder;
import com.cheng.domain.MiaoshaUser;
import com.cheng.domain.OrderInfo;
import com.cheng.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 15:18 2018/7/7
 * @Reference:
 */

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        goodsService.reduceStock(goods);

        //下订单
        OrderInfo orderInfo = orderService.createOrder(user, goods);

        /*Goods g = new Goods();
        g.setId(goods.getId());
        g.setGoodsStock(goods.getStockCount() - 1);*/

        return orderInfo;
    }

}