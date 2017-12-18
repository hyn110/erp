package com.fmi110.biz.impl;

import com.fmi110.biz.ReportBiz;
import com.fmi110.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by huangyunning on 2017/12/13.
 */
@Service
@Transactional
public class ReportBizImpl implements ReportBiz {

    @Autowired
    private ReportDao dao;

    @Override
    public List<Map<String,Object>> orderReport(Date date1, Date date2) {
        return dao.orderReport(date1,date2);
    }

    @Override
    public List<Map<String, Object>> trendReport(Integer year) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> maps = dao.trendReport(year);

        Map<Integer, Map<String, Object>> temp = new HashMap<>(); // 临时存储月份数据的map , 用于过滤
        for(Map<String, Object> m:maps){
            temp.put((Integer) m.get("month"), m);
        }
        for(int i=1;i<=12;i++){
            Map<String, Object> tt = temp.get(i);
            if(tt == null){
                Map<String, Object> t = new HashMap<>();
                t.put("month", i);
                t.put("value",0.00);
                temp.put(i, t);
            }
            list.add(temp.get(i));
        }

        return list;
    }

}

//SELECT
//        t1.UUID,
//        t1.NAME,
//        t1.storenum,
//        nvl(t2.outnum, 0) outnum
//        FROM
//        (SELECT
//        g.uuid,
//        g.NAME,
//        sum(CASE WHEN d.NUM IS NULL
//        THEN 0
//        ELSE d.NUM END) storenum
//        FROM GOODS g
//        LEFT JOIN
//        STOREDETAIL d
//        ON
//        g.UUID = d.GOODSUUID
//        GROUP BY
//        g.UUID, g.NAME) t1,
//        (SELECT
//        d.GOODSUUID,
//        sum(CASE WHEN d.NUM IS NULL
//        THEN 0
//        ELSE d.NUM END) outnum
//        FROM ORDERDETAIL d, ORDERS o
//        WHERE d.ORDERSUUID = o.UUID
//        AND o.TYPE = '2' AND d.STATE = '0'
//        GROUP BY
//        d.GOODSUUID) t2
//        WHERE t1.UUID = t2.GOODSUUID (+)