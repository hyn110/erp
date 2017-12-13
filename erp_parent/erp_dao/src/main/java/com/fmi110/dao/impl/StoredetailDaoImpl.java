package com.fmi110.dao.impl;

import com.fmi110.dao.StoredetailDao;
import com.fmi110.entity.Storedetail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class StoredetailDaoImpl extends BaseDaoImpl<Storedetail> implements StoredetailDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Storedetail t1, Storedetail t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Storedetail.class);

        if(t1!=null&&t1.getStore()!=null&&t1.getStore().getUuid()!=null){
            criteria.add(Restrictions.eq("store.uuid", t1.getStore()
                                                         .getUuid()));
        }
        if(t1!=null&&t1.getGoods()!=null&&t1.getGoods().getUuid()!=null){
            criteria.add(Restrictions.eq("store.uuid", t1.getGoods()
                                                         .getUuid()));
        }
        if(t1!=null&&t1.getNum()!=null){
            criteria.add(Restrictions.gt("num", t1.getNum()));
        }
        if(t2!=null&&t2.getNum()!=null){
            criteria.add(Restrictions.lt("num", t2.getNum()));
        }
        return criteria;
    }

    /**
     * 查询指定仓库里指定商品的库存
     * @param t1
     * @return
     */
    @Override
    public long findStoredCount(Storedetail t1) {
        String hql = "select count(*) from Storedetail where store.uuid = :suuid and goods.uuid=:guuid";
        List<Long> list = (List<Long>) super.getHibernateTemplate()
                                            .findByNamedParam(hql, new String[]{"suuid", "guuid"}, new Object[]{
                                            t1.getStore().getUuid(),
                                            t1.getGoods().getUuid()});
        if(list.size()>0){
            return list.get(0);
        }
        return 0;
    }
}
