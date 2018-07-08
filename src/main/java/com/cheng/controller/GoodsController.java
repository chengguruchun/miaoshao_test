package com.cheng.controller;

import com.cheng.domain.MiaoshaUser;
import com.cheng.redis.GoodsKey;
import com.cheng.redis.RedisService;
import com.cheng.service.GoodsService;
import com.cheng.service.MiaoshaUserService;
import com.cheng.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/to_listOld")
    public String toLogin1(HttpServletResponse response, Model model
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

    @RequestMapping("/to_list")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model
            ,MiaoshaUser user) {
        List<GoodsVo> goods = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goods);
        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(),applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }

        return html;
    }

    @RequestMapping("/to_detailOld/{goodId}")
    public String detailOld(Model model, MiaoshaUser user,
                         @PathVariable("goodId") long goodId) {

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int status = 0;
        int remainSeconds = 0;

        if (now < startTime) {
            status = 0;
            remainSeconds = (int) ((startTime - now)/1000);
        }else if(now > endTime) {
            status = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            status = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", status);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("user", user);
        return "goods_detail";
    }

    @RequestMapping(value = "/to_detail/{goodId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,
                         Model model, MiaoshaUser user,
                         @PathVariable("goodId") long goodId) {
        model.addAttribute("user", user);
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }


        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int status = 0;
        int remainSeconds = 0;

        if (now < startTime) {
            status = 0;
            remainSeconds = (int) ((startTime - now)/1000);
        }else if(now > endTime) {
            status = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            status = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", status);
        model.addAttribute("remainSeconds", remainSeconds);

        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(),applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodId, html);
        }

        return html;
    }

   /* @RequestMapping("/do_miaosha")
    public void do_miaosha() {

    }*/

}