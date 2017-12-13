package com.fmi110.web.controller;

import com.fmi110.biz.EmpBiz;
import com.fmi110.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/7.
 */
@Controller
//@SessionAttributes("user")
public class LoginController extends BaseController{

    @Autowired
    private EmpBiz service;

    //login_checkUser
    @RequestMapping(value="login_checkUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkUser(Emp entity, HttpSession session){
        encryptPwd(entity); //  对密码进行加密
        Emp emp = service.findByUsernameAndPwd(entity);
        Map<String, Object> map = new HashMap<>();
        if(emp==null){
            map.put("success",Boolean.FALSE);
            map.put("message", "用户名或密码错误");
        }else{
            map.put("success",Boolean.TRUE);
            map.put("message", "登录成功");
            session.setAttribute("user",emp);
        }
        return map;
    }


    //login_showName
    @RequestMapping(value="login_showName")
    @ResponseBody
    public Map<String, Object> showName(HttpSession session){
        Emp  emp = (Emp) session.getAttribute("user");

        System.out.println("emp = "+emp);

        Map<String, Object> map = new HashMap<>();
        if(emp!=null){
            map.put("success",Boolean.TRUE);
            map.put("message", emp.getUsername());
        }
        return map;
    }
    @RequestMapping(value="login_logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:login.html";
    }
}
