package com.fmi110.biz;
import com.fmi110.entity.Emp;
import com.fmi110.entity.Role;
import com.fmi110.entity.Tree;

import java.io.Serializable;
import java.util.List;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface RoleBiz extends BaseBiz<Role>{

    List<Tree> readRoleMenus(Long uuid);

    void updateRoleMenus(Long uuid,String checkdStr);
}
