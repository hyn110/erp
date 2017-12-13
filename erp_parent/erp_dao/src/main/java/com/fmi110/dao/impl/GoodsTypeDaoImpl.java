package com.fmi110.dao.impl;


import com.fmi110.dao.GoodsTypeDao;
import com.fmi110.entity.GoodsType;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsType> implements GoodsTypeDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(GoodsType t1, GoodsType t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(GoodsType.class);
        if(t1!=null&&!StringUtils.isEmpty(t1.getName())){
            criteria.add(Restrictions.like("name", t1.getName(), MatchMode.ANYWHERE));
        }
        return criteria;
    }
}
