package com.fmi110.web.controller;

import com.fmi110.biz.StoreoperBiz;
import com.fmi110.entity.Storeoper;
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
public class StoreoperController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StoreoperController.class);

    @Autowired
    private StoreoperBiz storeoperBiz;

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

    @RequestMapping("storeoper_list")
    @ResponseBody
    public List<Storeoper> list(){
        List<Storeoper> list = storeoperBiz.findAll();
        return list;
    }

    @RequestMapping("storeoper_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1")Storeoper t1, @ModelAttribute("t2")Storeoper t2, Integer page , Integer rows){
        Integer firstResult = null;
        Integer maxResult = null;
        if(page!=null&&rows!=null){
            firstResult = (page-1)*rows;
            maxResult = rows;
        }

        long total = storeoperBiz.findCount(t1,t2,null);

        List<Storeoper> list  = storeoperBiz.findPage(t1,t2,null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

//    @RequestMapping("storeoper_save")
//    @ResponseBody
//    public String save(Storeoper storeoper){
//        storeoperBiz.save(storeoper);
//        return "success";
//    }

    @RequestMapping("storeoper_del")
    @ResponseBody
    public String delete(Storeoper storeoper){
        storeoperBiz.delete(storeoper);
        return "success";
    }

    @RequestMapping("storeoper_update")
    @ResponseBody
    public String update(Storeoper storeoper){
        storeoperBiz.update(storeoper);
        return "success";
    }



}
