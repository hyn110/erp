package com.fmi110.biz;
import com.fmi110.entity.StoreAlert;
import com.fmi110.entity.Storedetail;

import java.util.List;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface StoredetailBiz extends BaseBiz<Storedetail>{
    /**
     * 获取库存预警列表
     * @return
     */
    List<StoreAlert> getStoreAlertList(Integer firstResult,Integer maxResult);

    /**
     * 发送预警邮件
     */
    void sendStoreAlertMail();

    Long getStoreAlertCount();
}
