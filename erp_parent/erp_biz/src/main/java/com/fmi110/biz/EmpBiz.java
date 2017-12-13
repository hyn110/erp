package com.fmi110.biz;
import com.fmi110.entity.Emp;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface EmpBiz extends BaseBiz<Emp>{

    Emp findByUsernameAndPwd(Emp entity);

    void updatePwd(String newpass, Emp emp) throws Exception;
}
