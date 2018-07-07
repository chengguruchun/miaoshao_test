package com.cheng.controller;

import com.cheng.redis.RedisService;
import com.cheng.result.CodeMsg;
import com.cheng.result.Result;
import com.cheng.service.MiaoshaUserService;
import com.cheng.util.ValidatorUtil;
import com.cheng.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 16:21 2018/7/1
 * @Reference:
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private RedisService redisService;

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin() {

        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo vo) {
        logger.info(vo.toString());

        /*//参数校验
        String passInput = vo.getPassword();
        String mobile = vo.getMobile();
        if (org.apache.commons.lang3.StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }

        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);

        }

        if (!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        //CodeMsg msg = miaoshaUserService.login(vo);
        miaoshaUserService.login(response, vo);
        /*if (msg.getCode() == 0) {
            return Result.success(CodeMsg.SUCCESS);
        }else {
            return Result.error(msg);
        }*/

        return Result.success(true);
    }

}