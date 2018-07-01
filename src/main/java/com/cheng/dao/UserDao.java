package com.cheng.dao;

import com.cheng.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 14:49 2018/6/27
 * @Reference:
 */
@Mapper
public interface UserDao {
    @Select("select * from user where id=#{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    public int insert(User user);

}