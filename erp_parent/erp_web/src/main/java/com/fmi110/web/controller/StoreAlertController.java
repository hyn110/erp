package com.fmi110.web.controller;

import com.fmi110.biz.StoreAlertBiz;
import com.fmi110.biz.StoredetailBiz;
import com.fmi110.entity.StoreAlert;
import com.fmi110.entity.Storedetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class StoreAlertController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StoreAlertController.class);

    @Autowired
    private StoredetailBiz service;


    @RequestMapping("storealert_storeAlertList")
    @ResponseBody
    public Map<String, Object> storeAlertList(Integer page,Integer rows){

        if(page==null||rows==null){
            page = 1;
            rows=10;
        }
        int firstResult = (page-1)*rows;
        int maxResult = rows;
        System.out.println(page);
        System.out.println(rows);
        long total = service.getStoreAlertCount();

        List<StoreAlert> list = service.getStoreAlertList(firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

}
