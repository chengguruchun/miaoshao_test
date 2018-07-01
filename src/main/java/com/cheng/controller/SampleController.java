package com.cheng.controller;

import com.cheng.domain.User;
import com.cheng.redis.RedisService;
import com.cheng.redis.UserKey;
import com.cheng.result.Result;
import com.cheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 11:20 2018/6/27
 * @Reference:
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("lcc", "hello大的 world我为发生的速度");
        return "hello";
    }

    @RequestMapping("db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);

        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();

        return Result.success(true);
    }

   /* @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet() {
        String k = redisService.get("k", String.class);

        return Result.success(k);
    }*/

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
         User user = redisService.get(UserKey.getById, "1", User.class);

        return Result.success(user);
    }

    /*@RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet() {
        boolean ret = redisService.set("key2", "hello lcc!");
        String str = redisService.get("key2", String.class);

        return Result.success(str);
    }*/

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("opop");
        redisService.set(UserKey.getById, "" + 1, user);

        return Result.success(true);
    }

    @RequestMapping("/redis/incr")
    @ResponseBody
    public  String redisIncr(){

        return String.valueOf(redisService.incr(UserKey.getById, "incr"));
    }

}