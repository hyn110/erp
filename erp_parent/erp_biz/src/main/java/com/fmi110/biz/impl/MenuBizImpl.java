package com.fmi110.biz.impl;

import com.fmi110.biz.MenuBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.MenuDao;
import com.fmi110.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class MenuBizImpl extends BaseBizImpl<Menu> implements MenuBiz {

    @Autowired
    private MenuDao dao;


    @Override
    public BaseDao<Menu> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
