package com.fmi110.dao.impl;

import com.fmi110.dao.StoreAlertDao;
import com.fmi110.entity.StoreAlert;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class StoreAlertDaoImpl extends BaseDaoImpl<StoreAlert> implements StoreAlertDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(StoreAlert t1, StoreAlert t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(StoreAlert.class);

        return criteria;
    }

    @Override
    public long getStoreAlertCount() {
        return 0;
    }

    @Override
    public List<StoreAlert> getStoreAlertList(int firstResult, int maxResult) {
        return null;
    }
}
