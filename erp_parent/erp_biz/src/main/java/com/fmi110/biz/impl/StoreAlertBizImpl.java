package com.fmi110.biz.impl;

import com.fmi110.biz.StoreAlertBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.StoreAlertDao;
import com.fmi110.entity.StoreAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class StoreAlertBizImpl extends BaseBizImpl<StoreAlert> implements StoreAlertBiz {

    @Autowired
    private StoreAlertDao dao;


    @Override
    public BaseDao<StoreAlert> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    @Override
    public long getStoreAlertCount() {
        return dao.getStoreAlertCount();
    }

    @Override
    public List<StoreAlert> getStoreAlertList(int firstResult, int maxResult) {
        return dao.getStoreAlertList(firstResult, maxResult);
    }
}
