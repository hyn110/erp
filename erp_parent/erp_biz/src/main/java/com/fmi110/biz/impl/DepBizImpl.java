package com.fmi110.biz.impl;

import com.fmi110.biz.DepBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.DepDao;
import com.fmi110.entity.Dep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class DepBizImpl extends BaseBizImpl<Dep> implements DepBiz {

    @Autowired
    private DepDao dao;


    @Override
    public BaseDao<Dep> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
