package com.cheng.controller;

import com.cheng.domain.MiaoshaUser;
import com.cheng.redis.RedisService;
import com.cheng.service.GoodsService;
import com.cheng.service.MiaoshaUserService;
import com.cheng.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 20:06 2018/7/1
 * @Reference:
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toLogin(HttpServletResponse response, Model model
                          ,MiaoshaUser user
                          //@CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                          //@RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken
                          ) {

        /*if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }

        String token = StringUtils.isEmpty(paramToken)? cookieToken : paramToken;

        MiaoshaUser user = miaoshaUserService.getByToken(response, token);*/
        List<GoodsVo> goods = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goods);
        model.addAttribute("user", user);
        return "goods_list";
    }

    //@RequestMapping("to_detail")


}