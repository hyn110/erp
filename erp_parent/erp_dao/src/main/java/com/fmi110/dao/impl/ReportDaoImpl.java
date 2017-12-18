package com.fmi110.dao.impl;

import com.fmi110.dao.ReportDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by huangyunning on 2017/12/13.
 */
@Repository
public class ReportDaoImpl extends HibernateDaoSupport implements ReportDao {

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

        hql = hql + "  GROUP BY t.name";


        return (List<Map<String, Object>>) getHibernateTemplate().find(hql, list.toArray());
    }

    /**
     * 统计指定年份的每个月的销售额
     *
     * @param year
     * @return
     */
//    select to_char(o.createtime,'yyyy-MM') ,sum(d.MONEY) from ORDERDETAIL d,ORDERS o
//
//    where
//      d.ORDERSUUID=o.UUID
//    AND
//      o.TYPE=1
//    GROUP BY
//      to_char(o.createtime,'yyyy-MM')
    @Override
    public List<Map<String, Object>> trendReport(Integer year) {
        String hql = "select new Map(month(o.createtime) as month,sum(d.money) as value) from Orderdetail d,Orders o " +
                     " where" +
                     " d.order=o and o.type='1' and year(o.createtime)=?" +
                     " group by" +
                     " month(o.createtime)";
        if (year == null) {
            year = Calendar.getInstance()
                           .get(Calendar.YEAR); // 获取当前时间对应的年份
        }
        return (List<Map<String, Object>>) getHibernateTemplate().find(hql, year);
    }

}
