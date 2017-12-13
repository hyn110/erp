package com.fmi110.dao;

import com.fmi110.entity.Storedetail;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface StoredetailDao extends BaseDao<Storedetail>{

    long findStoredCount(Storedetail t1);
}
