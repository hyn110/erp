package com.fmi110.web.controller;

import com.fmi110.biz.StoredetailBiz;
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
public class StoredetailController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StoredetailController.class);

    @Autowired
    private StoredetailBiz storedetailBiz;
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


    @RequestMapping("storedetail_list")
    @ResponseBody
    public List<Storedetail> list(){
        List<Storedetail> list = storedetailBiz.findAll();
        return list;
    }

    @RequestMapping("storedetail_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1")Storedetail t1, @ModelAttribute("t2")Storedetail t2, Integer page , Integer rows){
        logger.info("t1 = {},page = {}, rows={}", t1, page, rows);
        Integer firstResult = null;
        Integer maxResult = null;
        if(page!=null&&rows!=null){
            firstResult = (page-1)*rows;
            maxResult = rows;
        }

        long total = storedetailBiz.findCount(t1, t2, null);

        List<Storedetail> list  = storedetailBiz.findPage(t1, t2, null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

    @RequestMapping("storedetail_save")
    @ResponseBody
    public String save(Storedetail storedetail){
        storedetailBiz.save(storedetail);
        return "success";
    }

    @RequestMapping("storedetail_del")
    @ResponseBody
    public String delete(Storedetail storedetail){
        storedetailBiz.delete(storedetail);
        return "success";
    }

    @RequestMapping("storedetail_update")
    @ResponseBody
    public String update(Storedetail storedetail){
        storedetailBiz.update(storedetail);
        return "success";
    }



}
