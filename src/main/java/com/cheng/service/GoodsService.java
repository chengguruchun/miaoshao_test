package com.cheng.service;

import com.cheng.dao.GoodsDao;
import com.cheng.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 17:57 2018/7/2
 * @Reference:
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoods();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodId) {
        return goodsDao.getGoodsVoByGoodsId(goodId);
    }
}