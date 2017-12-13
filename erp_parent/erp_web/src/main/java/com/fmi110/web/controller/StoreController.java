package com.fmi110.web.controller;

import com.fmi110.biz.StoreBiz;
import com.fmi110.entity.Store;
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
public class StoreController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreBiz service;


    @InitBinder("t1") // 条件筛选时表单提交数据带的前缀
    public void initBinder1(WebDataBinder binder){
        binder.setFieldDefaultPrefix("t1.");
    }
    @InitBinder("t2") // 条件筛选时表单提交数据带的前缀
    public void initBinder2(WebDataBinder binder){
        binder.setFieldDefaultPrefix("t2.");
    }

    @InitBinder("t")  // 新增或更新请求时的表单数据带的前缀
    public void initBinder3(WebDataBinder binder){
        binder.setFieldDefaultPrefix("t.");
    }
    
    @RequestMapping("store_save")
    @ResponseBody
    public String add(@ModelAttribute("t")Store entity){
        service.save(entity);
        return "success";
    }

    @RequestMapping("store_del")
    @ResponseBody
    public String delete(Store entity){
        service.delete(entity);
        return "success";
    }

    @RequestMapping("store_update")
    @ResponseBody
    public String update(@ModelAttribute("t") Store entity){
        service.update(entity);
        return "success";
    }

    @RequestMapping("store_list")
    @ResponseBody
    public List<Store> list(){
        return service.findAll();
    }

    @RequestMapping("store_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1") Store t1,@ModelAttribute("t2") Store t2,
                                    Integer page,Integer rows){

        if(page==null||rows==null){
            page = 1;
            rows=10;
        }
        int firstResult = (page-1)*rows;
        int maxResult = rows;
        System.out.println(page);
        System.out.println(rows);
        long total = service.findCount(t1,t2,null);

        List<Store> list = service.findPage(t1,t2,null,firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

}
