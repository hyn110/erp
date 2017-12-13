package com.fmi110.biz.impl;

import com.fmi110.biz.EmpBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.EmpDao;
import com.fmi110.entity.Emp;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class EmpBizImpl extends BaseBizImpl<Emp> implements EmpBiz {

    @Autowired
    private EmpDao dao;


    @Override
    public BaseDao<Emp> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    @Override
    public Emp findByUsernameAndPwd(Emp entity) {
        return dao.findByUsernameAndPwd(entity.getUsername(),entity.getPwd());
    }

    @Override
    public void updatePwd(String newpass, Emp emp) throws Exception {
        // 先校验密码正确才进行更新
        Emp emp1 = dao.findById(emp.getUuid());

        if(!emp1.getPwd().equals(emp.getPwd())){
            throw new Exception("原密码不正确");
        }
        Md5Hash md5Hash = new Md5Hash(newpass, emp.getUsername(), 2);
        emp.setPwd(md5Hash.toString());
        dao.update(emp);
    }
}
