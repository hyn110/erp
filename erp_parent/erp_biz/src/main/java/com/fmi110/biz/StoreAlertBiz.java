package com.fmi110.biz;

import com.fmi110.entity.StoreAlert;

import java.util.List;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface StoreAlertBiz extends BaseBiz<StoreAlert>{

    long getStoreAlertCount();

    List<StoreAlert> getStoreAlertList(int firstResult, int maxResult);
}
