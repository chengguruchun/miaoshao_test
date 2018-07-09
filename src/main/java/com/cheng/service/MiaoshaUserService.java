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
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;

        }

        //从数据库取
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    //如何更新缓存
    //新更新数据库，然后更新redis缓存
    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        MiaoshaUser user = getByID(id);
        if (user == null) {
            throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass, user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);

        //处理缓存
        boolean flag = redisService.delete(MiaoshaUserKey.getById, "" + id);
        redisService.delete(MiaoshaUserKey.token, "" + id);

        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
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
       /* redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);*/
        this.addCookie(response, token, user);
        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

         MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长cookie时间
        if (user != null) {
            this.addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {

        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}