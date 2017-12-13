package com.fmi110.dao.impl;

import com.fmi110.dao.OrderdetailDao;
import com.fmi110.entity.Orderdetail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class OrderdetailDaoImpl extends BaseDaoImpl<Orderdetail> implements OrderdetailDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Orderdetail t1, Orderdetail t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Orderdetail.class);

//    
        return criteria;
    }

    @Override
    public boolean checkOrderIsAllIn(Long ordersuuid) {
        List<Long> list = (List<Long>) getHibernateTemplate().find("select count(*) from Orderdetail where " +
                                                                   "uuid = ? and state='0'", ordersuuid);
        return list.get(0)==0;
    }

    @Override
    public List<Orderdetail> findByOrderUuid(Long uuid) {
        if(uuid==null)
            return null;
        DetachedCriteria criteria = DetachedCriteria.forClass(Orderdetail.class);
        criteria.add(Restrictions.eq("order.uuid", uuid));
//
        return (List<Orderdetail>) getHibernateTemplate().findByCriteria(criteria);
    }

}
