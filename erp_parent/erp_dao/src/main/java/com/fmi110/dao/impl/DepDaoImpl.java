package com.fmi110.dao.impl;

import com.fmi110.dao.DepDao;
import com.fmi110.entity.Dep;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class DepDaoImpl extends BaseDaoImpl<Dep> implements DepDao {
    @Override
    public List<Dep> findList(Dep dep,Integer firstResut,Integer maxResult) {

        return super.findPage(dep,null,null,firstResut,maxResult);
    }

    @Override
    protected DetachedCriteria getDetachedCriteria(Dep dep, Dep t2, Object param) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dep.class);

        if (dep != null && !StringUtils.isEmpty(dep.getName())) {
            detachedCriteria.add(Restrictions.like("name", "%" + dep.getName() + "%"));
        }
        if (dep != null && !StringUtils.isEmpty(dep.getTele())) {
            detachedCriteria.add(Restrictions.like("tele", "%" + dep.getTele() + "%"));
        }
        return detachedCriteria;
    }
}
