package com.fmi110.dao;

import com.fmi110.entity.Emp;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface EmpDao extends BaseDao<Emp>{

    Emp findByUsernameAndPwd(String username, String pwd);
}
