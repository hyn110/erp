package com.fmi110.dao.impl;

import com.fmi110.dao.SupplierDao;
import com.fmi110.entity.Supplier;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements SupplierDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Supplier t1, Supplier t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);

//        name;
//        address;
//        contact;
//        tele;
//        email;
//        type;
        // username
        if(t1!=null&&!StringUtils.isEmpty(t1.getName())){
            criteria.add(Restrictions.like("name", t1.getName(), MatchMode.ANYWHERE));
        }
        //name
        if(t1!=null&&!StringUtils.isEmpty(t1.getAddress())){
            criteria.add(Restrictions.like("address", t1.getAddress(), MatchMode.ANYWHERE));
        }

        if(t1!=null&&!StringUtils.isEmpty(t1.getContact())){
            criteria.add(Restrictions.like("contact", t1.getContact(), MatchMode.ANYWHERE));
        }

        if(t1!=null&&!StringUtils.isEmpty(t1.getTele())){
            criteria.add(Restrictions.like("tele", t1.getTele(), MatchMode.ANYWHERE));
        }

        if(t1!=null&&!StringUtils.isEmpty(t1.getEmail())){
            criteria.add(Restrictions.like("email", t1.getEmail(), MatchMode.ANYWHERE));
        }

        if(t1!=null&&!StringUtils.isEmpty(t1.getType())){
            criteria.add(Restrictions.eq("type", t1.getType()));
        }

        return criteria;
    }
}
