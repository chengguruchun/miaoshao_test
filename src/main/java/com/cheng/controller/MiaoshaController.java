package com.cheng.controller;

import com.cheng.domain.MiaoshaOrder;
import com.cheng.domain.MiaoshaUser;
import com.cheng.domain.OrderInfo;
import com.cheng.redis.RedisService;
import com.cheng.result.CodeMsg;
import com.cheng.service.GoodsService;
import com.cheng.service.MiaoshaService;
import com.cheng.service.OrderService;
import com.cheng.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 15:06 2018/7/7
 * @Reference:
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, MiaoshaUser user,
                             @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null)
            return "login";

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        //判断库存
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //秒杀是否成功
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //减库存，下订单，写入秒杀订单(事务---原子操作)
        OrderInfo info = miaoshaService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo", info);
        model.addAttribute("goods", goodsVo);
        return "order_detail";


    }
}