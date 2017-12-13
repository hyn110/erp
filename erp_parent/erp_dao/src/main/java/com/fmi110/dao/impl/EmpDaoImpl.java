package com.fmi110.dao.impl;

import com.fmi110.dao.EmpDao;
import com.fmi110.entity.Emp;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Repository
public class EmpDaoImpl extends BaseDaoImpl<Emp> implements EmpDao {

    @Override
    protected DetachedCriteria getDetachedCriteria(Emp t1, Emp t2, Object param) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Emp.class);
        // username
        if(t1!=null&&!StringUtils.isEmpty(t1.getUsername())){
            criteria.add(Restrictions.like("username", t1.getUsername(), MatchMode.ANYWHERE));
        }
        //name
        if(t1!=null&&!StringUtils.isEmpty(t1.getName())){
            criteria.add(Restrictions.like("name", t1.getName(), MatchMode.ANYWHERE));
        }
        //gender
        if(t1!=null&&!StringUtils.isEmpty(t1.getGender())){
            criteria.add(Restrictions.eq("gender", t1.getGender()));
        }
        // email
        if(t1!=null&&!StringUtils.isEmpty(t1.getEmail())){
            criteria.add(Restrictions.like("email", t1.getEmail(), MatchMode.ANYWHERE));
        }
        // tele
        if(t1!=null&&!StringUtils.isEmpty(t1.getTele())){
            criteria.add(Restrictions.like("tele", t1.getTele(), MatchMode.ANYWHERE));
        }
        // address
        if(t1!=null&&!StringUtils.isEmpty(t1.getAddress())){
            criteria.add(Restrictions.like("address", t1.getAddress(), MatchMode.ANYWHERE));
        }
        // birthday
        if(t1!=null&&t1.getBirthday()!=null){
            criteria.add(Restrictions.ge("birthday", t1.getBirthday()));
        }
        // birthday
        if(t2!=null&&t2.getBirthday()!=null){
            criteria.add(Restrictions.le("birthday", t2.getBirthday()));
        }
        // dep.uuid
        if(t1!=null&&t1.getDep()!=null&&!StringUtils.isEmpty(t1.getDep().getUuid())){
            criteria.add(Restrictions.eq("dep.uuid", t1.getDep().getUuid()));
        }

        return criteria;
    }

    @Override
    public Emp findByUsernameAndPwd(String username, String pwd) {
        List<Emp> list = (List<Emp>) getHibernateTemplate().find("from Emp where username=? and pwd=?",
                                                                 username, pwd);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
