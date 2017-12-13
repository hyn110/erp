package com.fmi110.web.controller;

import com.fmi110.biz.DepBiz;
import com.fmi110.entity.Dep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class DepController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DepController.class);

    @Autowired
    private DepBiz depBiz;


    @RequestMapping("dep_list")
    @ResponseBody
    public List<Dep> list(){
        List<Dep> list = depBiz.findAll();
        return list;
    }

    @RequestMapping("dep_listByPage")
    @ResponseBody
    public Map<String, Object> list(Dep dep,Integer page , Integer rows){
        logger.info("dep = {},page = {}, rows={}",dep,page,rows);
        Integer firstResult = null;
        Integer maxResult = null;
        if(page!=null&&rows!=null){
            firstResult = (page-1)*rows;
            maxResult = rows;
        }

        long total = depBiz.findCount(dep,null,null);

        List<Dep> list  = depBiz.findPage(dep,null,null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

    @RequestMapping("dep_save")
    @ResponseBody
    public String save(Dep dep){
        depBiz.save(dep);
        return "success";
    }

    @RequestMapping("dep_del")
    @ResponseBody
    public String delete(Dep dep){
        depBiz.delete(dep);
        return "success";
    }

    @RequestMapping("dep_update")
    @ResponseBody
    public String update(Dep dep){
        depBiz.update(dep);
        return "success";
    }



}
