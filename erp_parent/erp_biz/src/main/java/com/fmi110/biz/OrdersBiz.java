package com.fmi110.biz;

import com.fmi110.entity.Orders;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface OrdersBiz extends BaseBiz<Orders>{

    void doCheck(Long uuid, Long orderUuid);

    void doStart(Long uuid, Long orderUuid);
}
