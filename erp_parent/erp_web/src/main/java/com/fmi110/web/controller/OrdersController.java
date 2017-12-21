package com.fmi110.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.fmi110.biz.OrdersBiz;
import com.fmi110.entity.Emp;
import com.fmi110.entity.Orderdetail;
import com.fmi110.entity.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Controller
public class OrdersController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrdersBiz ordersBiz;
//    @Autowired
//    private OrderdetailBiz orderdetailBiz;

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
    
    @RequestMapping("orders_save")
    @ResponseBody
    public Map<String, Object> add(@ModelAttribute("t")Orders entity,String jsonString) throws IOException {
        Map<String, Object> map = new HashMap<>();
        // 判断用户是否登录
        Emp emp = getLoginUser();
        if(emp==null || StringUtils.isEmpty(jsonString)){
            map.put("success", Boolean.FALSE);
            map.put("message", "添加失败...");
            return map;
        }
        // 设置订单创建人
//        entity.setCreater(emp.getUuid());
        entity.setCreater(emp); // 使用关联对象后的处理方法
        // 获取订单详情
//        ObjectMapper mapper = new ObjectMapper();
//        List<Orderdetail> orderdetails   = mapper.readValue(jsonString, new TypeReference<List<Orderdetail>>() {});

        List<Orderdetail> orderdetails  = JSONArray.parseArray(jsonString,Orderdetail.class);

        entity.setOrderdetails(orderdetails);
        ordersBiz.save(entity);
        return getResultMap(Boolean.TRUE,"订单添加成功");
    }

    @RequestMapping("orders_del")
    @ResponseBody
    public String delete(Orders entity){
        ordersBiz.delete(entity);
        return "success";
    }

    @RequestMapping("orders_update")
    @ResponseBody
    public String update(@ModelAttribute("t") Orders entity){
        ordersBiz.update(entity);
        return "success";
    }

    @RequestMapping("orders_list")
    @ResponseBody
    public List<Orders> list(){
        return ordersBiz.findAll();
    }

    @RequestMapping("orders_listByPage")
    @ResponseBody
    public Map<String, Object> list(@ModelAttribute("t1") Orders t1,@ModelAttribute("t2") Orders t2,
                                    Integer page,Integer rows){

        if(page==null||rows==null){
            page = 1;
            rows=10;
        }
        int firstResult = (page-1)*rows;
        int maxResult = rows;
        System.out.println(page);
        System.out.println(rows);
        long total = ordersBiz.findCount(t1, t2, null);

        List<Orders> list = ordersBiz.findPage(t1, t2, null, firstResult, maxResult);

        return getResultMapForListReq(total,list);
    }

    @RequestMapping("orders_doCheck")
    @ResponseBody
    public Map<String, Object> doCheck(@RequestParam("uuid") Long orderUuid){
        checkLoginState();
        ordersBiz.doCheck(getLoginUser().getUuid(),orderUuid);
        return getResultMap(Boolean.TRUE,"订单状态修改为已审核~");
    }
    @RequestMapping("orders_doStart")
    @ResponseBody
    public Map<String, Object> doStart(@RequestParam("uuid") Long orderUuid){
        checkLoginState();
        ordersBiz.doStart(getLoginUser().getUuid(),orderUuid);
        return getResultMap(Boolean.TRUE,"订单状态修改为已确认~");
    }

    /**
     * 导出指定的订单明细到excel
     * @return
     */
    @RequestMapping("orders_export")
    public ResponseEntity<byte[]> export(Long uuid) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024 * 32);
        ordersBiz.exportById(os,uuid);
        HttpHeaders headers = new HttpHeaders();

        String fileName = new String(("订单 "+uuid+" 详情.xls").getBytes(),"iso-8859-1");


        headers.setContentDispositionFormData("attachment",fileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(os.toByteArray(),headers, HttpStatus.CREATED);
    }

}
