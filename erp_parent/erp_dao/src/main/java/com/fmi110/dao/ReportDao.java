package com.fmi110.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface ReportDao{
    List<Map<String, Object>> orderReport(Date date1, Date date2);
}
