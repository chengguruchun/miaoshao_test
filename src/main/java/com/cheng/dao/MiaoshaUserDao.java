package com.cheng.dao;

import com.cheng.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 16:49 2018/7/1
 * @Reference:
 */
@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);

    @Update("update miaosha_user set password = #{password } where id = #{id}")
    public void update(MiaoshaUser toBeUpdate);
}