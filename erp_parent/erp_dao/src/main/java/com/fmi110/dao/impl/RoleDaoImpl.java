package com.fmi110.dao.impl;

import com.fmi110.dao.RoleDao;
import com.fmi110.entity.Role;
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
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Role t1, Role t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
       
        return criteria;
    }

   
}
