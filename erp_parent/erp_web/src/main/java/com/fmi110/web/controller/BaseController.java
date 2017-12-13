package com.fmi110.web.controller;

import com.fmi110.entity.Emp;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/6.
 */
public class BaseController {
    /**
     * 当前登录的用户
     */
    protected Emp emp;
    protected HttpSession session;
    /**
     * 处理当前Controller抛出的异常
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public String exception(Model model, Exception ex){
        ex.printStackTrace();
//         mv = new ModelAndView("error.jsp");
//        model.addAttribute("exception", ex);
        return ex.getLocalizedMessage();
    }

    /**
     * 使用MD5Hash 对密码进行加密
     * @param entity
     */
    public void encryptPwd(Emp entity) {
        // 加密密码
        String  pwd     = entity.getPwd();
        Md5Hash md5Hash = new Md5Hash(pwd, entity.getUsername(), 2);
        pwd = md5Hash.toString();

        System.out.println("加密后的pwd = "+pwd);
        entity.setPwd(pwd);
    }
    @ModelAttribute
    public void bindSession(HttpSession session){
       this.session = session;
    }

    public Emp getLoginUser(){
        if(session!=null){
          emp = (Emp) session.getAttribute("user");
        }
        System.out.println("==== 从baseController 获取当前登录用户 : "+emp);
        return emp;
    }

    /**
     * 检查用户登录状态
     */
    public void checkLoginState(){
        if(getLoginUser()==null)
            throw new RuntimeException("请先登录再进行操作...");
    }

    /**
     * 返回结果map
     * @param success 请求是否成功
     * @param message 返回的信息
     * @return
     */
    public Map<String, Object> getResultMap(boolean success, Object message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.TRUE);
        map.put("message", message);
        return map;
    }

    /**
     * 列表请求时返回的结果
     * @param total 条目总数
     * @param rows  条目集合
     * @return
     */
    public Map<String, Object> getResultMapForListReq(long total, Object rows) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);
        return map;
    }
}
