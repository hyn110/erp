package com.fmi110.biz.impl;

import com.fmi110.biz.ReportBiz;
import com.fmi110.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
