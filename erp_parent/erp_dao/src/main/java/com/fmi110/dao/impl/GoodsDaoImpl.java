package com.fmi110.dao.impl;

import com.fmi110.dao.GoodsDao;
import com.fmi110.entity.Goods;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class GoodsDaoImpl extends BaseDaoImpl<Goods> implements GoodsDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Goods t1, Goods t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Goods.class);

//        name:
        if(t1!=null&&!StringUtils.isEmpty(t1.getName())){
            criteria.add(Restrictions.like("name", t1.getName(), MatchMode.ANYWHERE));
        }
//        origin:
        if(t1!=null&&!StringUtils.isEmpty(t1.getOrigin())){
            criteria.add(Restrictions.like("origin", t1.getOrigin(), MatchMode.ANYWHERE));
        }
//        producer:
        if(t1!=null&&!StringUtils.isEmpty(t1.getProducer())){
            criteria.add(Restrictions.like("producer", t1.getProducer(), MatchMode.ANYWHERE));
        }
//        unit:
        if(t1!=null&&!StringUtils.isEmpty(t1.getUnit())){
            criteria.add(Restrictions.like("unit", t1.getUnit(), MatchMode.ANYWHERE));
        }
//        t1.inprice 进货低:
        if(t1!=null&&!StringUtils.isEmpty(t1.getInprice())){
            criteria.add(Restrictions.ge("inprice", t1.getInprice()));
        }
        // 进货高价位
        if(t2!=null&&!StringUtils.isEmpty(t2.getInprice())){
            criteria.add(Restrictions.le("inprice", t2.getInprice()));
        }
//        t1.outprice:
        if(t1!=null&&!StringUtils.isEmpty(t1.getOutprice())){
            criteria.add(Restrictions.ge("outprice", t1.getOutprice()));
        }
        if(t2!=null&&!StringUtils.isEmpty(t2.getOutprice())){
            criteria.add(Restrictions.le("outprice", t2.getOutprice()));
        }
//        goodsType.uuid:
        if(t1!=null&&t1.getGoodsType()!=null&&t1.getGoodsType().getUuid()!=null){
            criteria.add(Restrictions.eq("goodsType.uuid", t1.getGoodsType().getUuid()));
        }
        return criteria;
    }
}
