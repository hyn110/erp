package com.fmi110.biz.impl;

import com.fmi110.biz.StoreoperBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.StoreoperDao;
import com.fmi110.entity.Storeoper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class StoreoperImpl extends BaseBizImpl<Storeoper> implements StoreoperBiz {

    @Autowired
    private StoreoperDao dao;


    @Override
    public BaseDao<Storeoper> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
