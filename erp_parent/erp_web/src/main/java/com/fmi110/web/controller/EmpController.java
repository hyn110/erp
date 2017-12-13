package com.fmi110.web.controller;

import com.fmi110.biz.EmpBiz;
import com.fmi110.entity.Emp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class EmpController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpBiz service;


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
    
    @RequestMapping("emp_save")
    @ResponseBody
    public String add(@ModelAttribute("t")Emp entity){
        // 设置新员工的初始密码为用户名
        entity.setPwd(entity.getUsername());
        encryptPwd(entity);
        service.save(entity);
        return "success";
    }



    @RequestMapping("emp_del")
    @ResponseBody
    public String delete(Emp entity){
        service.delete(entity);
        return "success";
    }

    /**
     * 这里不更新密码
     * @param entity
     * @return
     */
    @RequestMapping("emp_update")
    @ResponseBody
    public String update(@ModelAttribute("t") Emp entity){
        Emp id = service.findById(entity.getUuid());
        entity.setPwd(id.getPwd());// 还原为原来的密码
        service.update(entity);
        return "success";
    }
    //emp_updatePwd_reset
    @RequestMapping("emp_updatePwd_reset")
    @ResponseBody
    public Map<String,Object> update(Long uuid,String newPwd){
        Emp emp = service.findById(uuid);
        emp.setPwd(newPwd);
        encryptPwd(emp);
        service.update(emp);
        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.TRUE);
        map.put("message", "操作成功!!");
        return map;
    }
    @RequestMapping("emp_updatePwd")
    @ResponseBody
    public String update(String newpass, HttpSession session) throws Exception {
        Emp emp = (Emp) session.getAttribute("user");
        if(newpass!=null){
            emp.setPwd(newpass);
            encryptPwd(emp); // 加密密码
            service.updatePwd(newpass,emp);
        }
        return "success";
    }


    @RequestMapping("emp_list")
    @ResponseBody
    public List<Emp> list(){
        return service.findAll();
    }

    @RequestMapping("emp_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1") Emp t1,@ModelAttribute("t2") Emp t2,
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

        List<Emp> list = service.findPage(t1,t2,null,firstResult, maxResult);

//        Map<String, Object> map = new HashMap<>();
//        map.put("total", total);
//        map.put("rows", list);
        return getResultMapForListReq(total,list);
    }


}
