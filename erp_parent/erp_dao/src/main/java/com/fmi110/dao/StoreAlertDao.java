package com.fmi110.dao;

import com.fmi110.entity.StoreAlert;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface StoreAlertDao extends BaseDao<StoreAlert>{

    long getStoreAlertCount();

    List<StoreAlert> getStoreAlertList(int firstResult, int maxResult);
}
