package com.fmi110.biz.impl;

import com.fmi110.biz.GoodsBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.GoodsDao;
import com.fmi110.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class GoodsBizImpl extends BaseBizImpl<Goods> implements GoodsBiz {

    @Autowired
    private GoodsDao dao;


    @Override
    public BaseDao<Goods> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
