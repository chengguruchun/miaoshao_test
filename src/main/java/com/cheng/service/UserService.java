package com.cheng.service;

import com.cheng.dao.UserDao;
import com.cheng.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 14:52 2018/6/27
 * @Reference:
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;
    public User getById(int id) {

        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("lcc");

        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("op");
        userDao.insert(u2);
        return true;
    }

}