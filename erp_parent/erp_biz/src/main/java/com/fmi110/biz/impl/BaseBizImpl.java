package com.fmi110.biz.impl;

import com.fmi110.biz.BaseBiz;
import com.fmi110.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * 业务层的基类实现类,因为要解决 dao 依赖的注入问题,所以使用抽象方法
 * getBaseDao()  从子类中获取到 dao 对象
 * Created by huangyunning on 2017/12/4.
 */
@Transactional  // 基类也需要开启事务否则报没事务异常
public abstract class BaseBizImpl<T> implements BaseBiz<T> {

    private BaseDao<T> baseDao;
    @PostConstruct
    public void initBaseDao(){
        this.baseDao = getBaseDao();
        System.out.println("=====BaseBizImpl 业务层注入 dao : "+baseDao);
    }

    /**
     * 获取basedao,要求子类必须实现
     */
    public abstract BaseDao<T> getBaseDao() ;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void save(T t) {
        baseDao.save(t);
    }

    @Override
    public long findCount(T t1,T t2,Object param) {
        return baseDao.findCount(t1,t2,param);
    }

    @Override
    public long findCount() {
        return baseDao.findCount();
    }

    @Override
    public List<T> findPage(Integer firstResult, Integer maxResults) {
        return baseDao.findPage(firstResult,maxResults);
    }

   @Override
   public List<T> findPage(T t1,T t2,Object param, Integer firstResult, Integer maxResult){
        return baseDao.findPage(t1,t2,param,firstResult,maxResult);
   }

    @Override
    public List<T> findAll() {
        return this.baseDao.findAll();
    }

    @Override
    public T findById(Serializable id) {
        return baseDao.findById(id);
    }

    @Override
    public void update(T t) {
        baseDao.update(t);
    }

    @Override
    public void delete(T t) {
        baseDao.delete(t);
    }
}
