package com.fmi110.biz.impl;

import com.fmi110.biz.GoodsTypeBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.GoodsTypeDao;
import com.fmi110.entity.GoodsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class GoodsTypeBizImpl extends BaseBizImpl<GoodsType> implements GoodsTypeBiz {

    @Autowired
    private GoodsTypeDao dao;


    @Override
    public BaseDao<GoodsType> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
