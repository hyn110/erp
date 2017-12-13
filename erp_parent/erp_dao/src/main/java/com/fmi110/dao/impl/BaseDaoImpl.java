package com.fmi110.dao.impl;

import com.fmi110.dao.BaseDao;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * 带泛型,实现对象的增删改查
 */
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    private Class<?> clazz;// 代表T的类型

    public BaseDaoImpl() {
        // 获取当前的泛型类型

        Class<?> currentClazz = this.getClass();

        // 取值是 <T,A,B,C>
        Type type = currentClazz.getGenericSuperclass();
        // 1.泛型出现的位置
        ParameterizedType t = (ParameterizedType) type;
        // 2.泛型的多少 T
        Type actualType = t.getActualTypeArguments()[0];
        clazz = (Class<?>) actualType;
        System.out.println("BaseDaoImpl 泛型参数的类为 : " + clazz);
        // clazz = ?;
    }

    @Autowired
    public void injectSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(T t) {
        getHibernateTemplate().save(t);
    }

    @Override
    public long findCount() {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        // 查询条数(count(*))
        criteria.setProjection(Projections.rowCount());
        HibernateTemplate template = getHibernateTemplate();
        List<Long>        results  = (List<Long>) template.findByCriteria(criteria);
        if (results != null && results.size() > 0) {
            return (long) results.get(0);
        } else {
            return 0;
        }
    }

    @Override
    public long findCount(T t1, T t2, Object param) {
        DetachedCriteria criteria = this.getDetachedCriteria(t1, t2, param);
        criteria.setProjection(Projections.rowCount());
        HibernateTemplate template = getHibernateTemplate();
        List<Long>        results  = (List<Long>) template.findByCriteria(criteria);
        if (results != null && results.size() > 0) {
            return (long) results.get(0);
        } else {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPage(Integer firstResult, Integer maxResult) {
        // 重置查询(*)
        DetachedCriteria  criteria = DetachedCriteria.forClass(clazz);
        HibernateTemplate template = getHibernateTemplate();
        if(firstResult==null||maxResult==null){
            return (List<T>) getHibernateTemplate().findByCriteria(criteria);
        }
        return (List<T>) template.findByCriteria(criteria, firstResult, maxResult);
    }

    @Override
    public List<T> findPage(T t1, T t2, Object param, Integer firstResult, Integer maxResult) {
        DetachedCriteria criteria = this.getDetachedCriteria(t1, t2, param);
        if(firstResult==null||maxResult==null){
            return (List<T>) getHibernateTemplate().findByCriteria(criteria);
        }
        return (List<T>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
    }

    /**
     * 由子类实现,构建分页的查询条件
     *
     * @param t1
     * @param t2
     * @param param
     * @return
     */
    protected abstract DetachedCriteria getDetachedCriteria(T t1, T t2, Object param);

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        // 重置查询(*)
        DetachedCriteria  criteria = DetachedCriteria.forClass(clazz);
        HibernateTemplate template = getHibernateTemplate();
        return (List<T>) template.findByCriteria(criteria);
    }

    @Override
    public void update(T t) {
        // 清除以关联的对象,当已经存在持久态对象时,想要更新具有相同id的游离态对象时,需要clear
        getHibernateTemplate().clear();
        getHibernateTemplate().update(t);
    }

    @Override
    public void delete(T t) {
        getHibernateTemplate().delete(t);
    }


    @Override
    public T findById(Serializable id) {
        return (T) getHibernateTemplate().get(clazz, id);
    }

}
