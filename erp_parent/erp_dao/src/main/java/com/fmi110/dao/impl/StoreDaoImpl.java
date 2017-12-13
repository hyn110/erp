package com.fmi110.dao.impl;

import com.fmi110.dao.StoreDao;
import com.fmi110.entity.Store;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class StoreDaoImpl extends BaseDaoImpl<Store> implements StoreDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Store t1, Store t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Store.class);
        if(t1!=null&&!StringUtils.isEmpty(t1.getName())){
            criteria.add(Restrictions.like("name", t1.getName(), MatchMode.ANYWHERE));
        }
        // 员工编号
        return criteria;
    }
}
