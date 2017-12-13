package com.fmi110.biz.impl;

import com.fmi110.biz.StoreBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.StoreDao;
import com.fmi110.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class StoreBizImpl extends BaseBizImpl<Store> implements StoreBiz {

    @Autowired
    private StoreDao dao;


    @Override
    public BaseDao<Store> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
