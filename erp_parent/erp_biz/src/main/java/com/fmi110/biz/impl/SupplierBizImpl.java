package com.fmi110.biz.impl;

import com.fmi110.biz.SupplierBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.SupplierDao;
import com.fmi110.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class SupplierBizImpl extends BaseBizImpl<Supplier> implements SupplierBiz {

    @Autowired
    private SupplierDao dao;


    @Override
    public BaseDao<Supplier> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }
}
