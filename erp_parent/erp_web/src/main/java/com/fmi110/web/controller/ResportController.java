package com.fmi110.web.controller;

import com.fmi110.biz.ReportBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class ResportController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ResportController.class);
    @Autowired
    private ReportBiz service;

    @RequestMapping("report_orderReport")
    @ResponseBody
    public List<Map<String, Object>> orderReport(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date1,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date2) {

        return service.orderReport(date1, date2);
    }

    @RequestMapping("report_trendReport")
    @ResponseBody
    public List<Map<String, Object>> trendReport(Integer year) {

        return service.trendReport(year);
    }

}
