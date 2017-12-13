package com.fmi110.dao.impl;

import com.fmi110.dao.MenuDao;
import com.fmi110.entity.Menu;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Menu t1, Menu t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        return criteria;
    }
}
