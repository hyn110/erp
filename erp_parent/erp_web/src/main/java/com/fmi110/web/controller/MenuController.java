package com.fmi110.web.controller;

import com.fmi110.biz.MenuBiz;
import com.fmi110.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangyunning on 2017/12/7.
 */
@Controller
public class MenuController extends BaseController {

    @Autowired
    private MenuBiz service;

    @RequestMapping("menu_getMenuTree")
    @ResponseBody
    public Menu getMenuTree(){
        Menu menu = service.findById("0");
        return menu;
    }
}
