package com.fmi110.dao.impl;

import com.fmi110.dao.ReportDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/13.
 */
@Repository
public class ReportDaoImpl extends HibernateDaoSupport implements ReportDao{

    @Autowired
    public void injectSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public List<Map<String, Object>> orderReport(Date date1, Date date2) {
        String hql = "SELECT new Map(t.name as name,sum(d.money) as value)" +
                     " FROM Orderdetail d, GoodsType t, Goods g, Orders o" +
                     "" +
                     "  WHERE" +
                     "  d.goods = g" +
                     "  AND" +
                     "  t = g.goodsType" +
                     "  AND" +
                     "  d.order = o" +
                     "  AND o.type = '1'";
//                     "  GROUP BY" +
//                     "  t.name";
        ArrayList<Date> list = new ArrayList<>();
        if (date1 != null) {
            hql = hql + " and o.createtime>?";
            list.add(date1);
        }
        if (date2 != null) {
            hql = hql + " and o.createtime<?";
            list.add(date2);
        }

        hql = hql+ "  GROUP BY t.name";


        return (List<Map<String, Object>>) getHibernateTemplate().find(hql, list.toArray());
    }

}
