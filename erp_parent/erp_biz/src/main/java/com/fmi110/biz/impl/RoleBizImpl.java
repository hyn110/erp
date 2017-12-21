package com.fmi110.biz.impl;

import com.fmi110.biz.RoleBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.MenuDao;
import com.fmi110.dao.RoleDao;
import com.fmi110.entity.Menu;
import com.fmi110.entity.Role;
import com.fmi110.entity.Tree;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class RoleBizImpl extends BaseBizImpl<Role> implements RoleBiz {

    @Autowired
    private RoleDao dao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public BaseDao<Role> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    /**
     * 读取角色权限表
     * @param uuid
     * @return
     */
    @Override
    public List<Tree> readRoleMenus(Long uuid) {
        List<Tree> list = new ArrayList<>();
        Menu menus = menuDao.findById("0");

        // 获取角色列表
        List<Menu> roleMenus = dao.findById(uuid)
                               .getMenus();


        Tree t1=null,t2=null;
        for (Menu m1 : menus.getMenus()) {
            t1 = new Tree();
            t1.setText(m1.getMenuname());
            t1.setId(m1.getMenuid());
            for (Menu m2 : m1.getMenus()) {
                t2 = new Tree();
                t2.setId(m2.getMenuid());
                t2.setText(m2.getMenuname());
                if(roleMenus.contains(m2)) {  // 包含角色时添加菜单
//                    t1.getChildren()
//                      .add(t2);
                    t2.setChecked(true);
                }
                t1.getChildren()
                  .add(t2);
            }
            list.add(t1);
        }

        return list;
    }

    /**
     * 更新角色权限表
     * @param uuid
     * @param checkdStr
     */
    @Override
    public void updateRoleMenus(Long uuid, String checkdStr) {
        Role role = dao.findById(uuid);
        role.setMenus(new ArrayList<>());
        String[] ids = checkdStr.split(",");
        for (String id : ids) {
            Menu menu = menuDao.findById(id);
            role.getMenus()
                .add(menu);
        }
    }
}
