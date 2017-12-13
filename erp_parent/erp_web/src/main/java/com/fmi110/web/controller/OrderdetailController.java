package com.fmi110.web.controller;

import com.fmi110.biz.OrderdetailBiz;
import com.fmi110.entity.Orderdetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class OrderdetailController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderdetailController.class);

    @Autowired
    private OrderdetailBiz ordersdetailBiz;


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
    
    @RequestMapping("orderdetail_save")
    @ResponseBody
    public Map<String, Object> add(@ModelAttribute("t")Orderdetail entity,String jsonString) throws IOException {
//        Map<String, Object> map = new HashMap<>();
//        // 判断用户是否登录
//        Emp emp = getLoginUser();
//        if(emp==null || StringUtils.isEmpty(jsonString)){
//            map.put("success", Boolean.FALSE);
//            map.put("message", "添加失败...");
//            return map;
//        }

        return getResultMap(Boolean.TRUE,"订单添加成功");
    }

    @RequestMapping("orderdetail_del")
    @ResponseBody
    public String delete(Orderdetail entity){
        ordersdetailBiz.delete(entity);
        return "success";
    }

    @RequestMapping("orderdetail_update")
    @ResponseBody
    public String update(@ModelAttribute("t") Orderdetail entity){
        ordersdetailBiz.update(entity);
        return "success";
    }

    @RequestMapping("orderdetail_list")
    @ResponseBody
    public List<Orderdetail> list(){
        return ordersdetailBiz.findAll();
    }

    @RequestMapping("ordersdetail_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1") Orderdetail t1,@ModelAttribute("t2") Orderdetail t2,
                                    Integer page,Integer rows){

        if(page==null||rows==null){
            page = 1;
            rows=10;
        }
        int firstResult = (page-1)*rows;
        int maxResult = rows;
        System.out.println(page);
        System.out.println(rows);
        long total = ordersdetailBiz.findCount(t1, t2, null);

        List<Orderdetail> list = ordersdetailBiz.findPage(t1, t2, null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

    @RequestMapping("orderdetail_doInStore")
    @ResponseBody
    public Map<String, Object> list(Long id, Long storeuuid){
        checkLoginState();
        Orderdetail orderdetail = ordersdetailBiz.findById(id);
        ordersdetailBiz.doInStore(orderdetail,storeuuid,getLoginUser());

        return getResultMap(Boolean.TRUE,"订单详情入库成功");
    }
}
