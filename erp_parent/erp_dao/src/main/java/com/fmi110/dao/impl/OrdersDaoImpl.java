package com.fmi110.dao.impl;

import com.fmi110.dao.OrdersDao;
import com.fmi110.entity.Orders;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class OrdersDaoImpl extends BaseDaoImpl<Orders> implements OrdersDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Orders t1, Orders t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Orders.class);
        // 订单状态
        if(t1!=null&&!StringUtils.isEmpty(t1.getState())){
            criteria.add(Restrictions.eq("state", t1.getState()));
        }

        // 订单类型
        if(t1!=null&&!StringUtils.isEmpty(t1.getType())){
            criteria.add(Restrictions.eq("type", t1.getType()));
        }

        return criteria;
    }
}
