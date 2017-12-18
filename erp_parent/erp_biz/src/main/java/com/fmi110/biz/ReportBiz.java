package com.fmi110.biz;

import com.fmi110.entity.Store;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface ReportBiz{
    List<Map<String,Object>> orderReport(Date date1, Date date2);
    List<Map<String,Object>> trendReport(Integer year);
}
