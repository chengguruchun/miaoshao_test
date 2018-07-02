package com.cheng.service;

import com.cheng.dao.MiaoshaUserDao;
import com.cheng.domain.MiaoshaUser;
import com.cheng.exception.GlobalException;
import com.cheng.redis.MiaoshaUserKey;
import com.cheng.redis.RedisService;
import com.cheng.result.CodeMsg;
import com.cheng.util.MD5Util;
import com.cheng.util.UUIDUtil;
import com.cheng.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 16:51 2018/7/1
 * @Reference:
 */
@Service
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    @Autowired
    RedisService redisService;

    public MiaoshaUser getByID(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo vo) {
        if (vo == null)
           throw new GlobalException(CodeMsg.SERVER_ERROR);

        String mobile = vo.getMobile();
        String password = vo.getPassword();
        MiaoshaUser user = getByID(Long.parseLong(mobile));

        if(user == null) {
            //return CodeMsg.MOBILE_NOT_EXIST;
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();

        String calcPass = MD5Util.formPassToDbPass(password, saltDB);
        if (!calcPass.equals(dbPass)) {
            //return CodeMsg.PASSWORD_ERROR;
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //登录成功之后设置cookie，返回sessionId
        //生成cookie
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

         MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长cookie时间
        if (user != null) {
            addCookie(response, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user) {
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}