package com.fmi110.dao;

import com.fmi110.entity.Orderdetail;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface OrderdetailDao extends BaseDao<Orderdetail>{

    boolean checkOrderIsAllIn(Long ordersuuid);

    List<Orderdetail> findByOrderUuid(Long uuid);
}
