package com.fmi110.dao.impl;

import com.fmi110.dao.StoreoperDao;
import com.fmi110.entity.Storeoper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class StoreoperDaoImpl extends BaseDaoImpl<Storeoper> implements StoreoperDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Storeoper t1, Storeoper t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Storeoper.class);

        if(t1!=null&&t1.getEmp()!=null&&t1.getEmp().getUuid()!=null){
            criteria.add(Restrictions.eq("emp.uuid", t1.getEmp()
                                                       .getUuid()));
        }
        /**
         *  比较到秒的条件查询,oracle 不区分大小写 所以时间的 分 需要用 mi 表示
         *  要表示 24 小时 需要使用 HH24
         *  select
         *      count(*) as y0_
         *  from
         *      Storeoper this_
         *  where
         *      this_.OPERTIME>=to_date('2017-12-11 19:00:11','yyyy-MM-dd HH24:mi:ss');
         *
         */



        if(t1!=null&&t1.getOpertime()!=null){
//            criteria.add(Restrictions.ge("opertime", t1.getOpertime()));
//            criteria.add(Restrictions.sqlRestriction("{alias}.opertime>=to_date(?,'yyyy-MM-dd HH:mm:ss')", t1.getOpertime(),TimestampType.INSTANCE));
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t1.getOpertime());
            criteria.add(Restrictions.sqlRestriction("{alias}.OPERTIME>=to_date('"+time+"','yyyy-MM-dd HH24:mi:ss')"));
        }
        if(t2!=null&&t2.getOpertime()!=null){
            criteria.add(Restrictions.le("opertime", t2.getOpertime()));
//            criteria.add(Restrictions.sqlRestriction("opertime<=?", t2.getOpertime(),TimestampType.INSTANCE));
        }
        if(t1!=null&&t1.getStore()!=null&&t1.getStore().getUuid()!=null){
            criteria.add(Restrictions.eq("store.uuid", t1.getStore()
                                                       .getUuid()));
        }
        if(t1!=null&&t1.getGoods()!=null&&t1.getGoods().getUuid()!=null){
            criteria.add(Restrictions.eq("goods.uuid", t1.getGoods()
                                                       .getUuid()));
        }
        if(t1!=null&&!StringUtils.isEmpty(t1.getType())){
            criteria.add(Restrictions.eq("type", t1.getType()));
        }

        return criteria;
    }
}
