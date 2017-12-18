package com.fmi110.biz.impl;

import com.fmi110.biz.StoredetailBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.StoredetailDao;
import com.fmi110.entity.StoreAlert;
import com.fmi110.entity.Storedetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class StoredetailBizImpl extends BaseBizImpl<Storedetail> implements StoredetailBiz {

    @Autowired
    private StoredetailDao dao;


    @Override
    public BaseDao<Storedetail> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }


    @Override
    public List<StoreAlert> getStoreAlertList(Integer firstResult, Integer maxResult) {
        return dao.getStoreAlertList(firstResult,maxResult);
    }

    @Override
    public void sendStoreAlertMail() {

    }

    @Override
    public Long getStoreAlertCount() {
        return dao.getStoreAlertCount();
    }
}
