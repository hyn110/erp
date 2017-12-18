package com.fmi110.dao;

import com.fmi110.entity.StoreAlert;
import com.fmi110.entity.Storedetail;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface StoredetailDao extends BaseDao<Storedetail>{

    long findStoredCount(Storedetail t1);

    /**
     * 获取库存预警列表
     * @return
     */
    List<StoreAlert> getStoreAlertList(Integer firstResult, Integer maxResult);

    Long getStoreAlertCount();
}
