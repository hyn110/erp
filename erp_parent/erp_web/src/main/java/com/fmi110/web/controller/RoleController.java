package com.fmi110.web.controller;

import com.fmi110.biz.RoleBiz;
import com.fmi110.entity.Role;
import com.fmi110.entity.Tree;
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
public class RoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleBiz roleBiz;
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


    @RequestMapping("role_list")
    @ResponseBody
    public List<Role> list(){
        List<Role> list = roleBiz.findAll();
        return list;
    }

    @RequestMapping("role_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1")Role t1, @ModelAttribute("t2")Role t2, Integer page , Integer rows){
        logger.info("t1 = {},page = {}, rows={}", t1, page, rows);
        Integer firstResult = null;
        Integer maxResult = null;
        if(page!=null&&rows!=null){
            firstResult = (page-1)*rows;
            maxResult = rows;
        }

        long total = roleBiz.findCount(t1, t2, null);

        List<Role> list  = roleBiz.findPage(t1, t2, null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

    @RequestMapping("role_save")
    @ResponseBody
    public String save(@ModelAttribute("t")Role role){
        roleBiz.save(role);
        return "success";
    }

    @RequestMapping("role_del")
    @ResponseBody
    public String delete(@ModelAttribute("t")Role role){
        roleBiz.delete(role);
        return "success";
    }

    @RequestMapping("role_update")
    @ResponseBody
    public String update(@ModelAttribute("t")Role role){
        roleBiz.update(role);
        return "success";
    }

    @RequestMapping("role_readRoleMenus")
    @ResponseBody
    public List<Tree> readRoleMenus(Long uuid){
        return roleBiz.readRoleMenus(uuid);
    }


    @RequestMapping("role_updateRoleMenus")
    @ResponseBody
    public Map<String, Object> updateRoleMenus(Long uuid,String checkedStr){

        roleBiz.updateRoleMenus(uuid,checkedStr);
        return getResultMap(true, "success");
    }
}
