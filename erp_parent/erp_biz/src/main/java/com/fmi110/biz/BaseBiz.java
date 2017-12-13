package com.fmi110.biz;

import java.io.Serializable;
import java.util.List;

/**
 * 业务层的基类
 * Created by huangyunning on 2017/12/4.
 */
public interface BaseBiz<T> {
    /**
     * 保存
     *
     * @param t
     */
    void save(T t);

    /**
     * 查询总数
     *
     * @return
     */
    long findCount();

    long findCount(T t1,T t2,Object param);

    /**
     * 分页查找
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    List<T> findPage(Integer firstResult, Integer maxResults);

    /**
     * 条件查询的分页查询
     */
    List<T> findPage(T t1,T t2,Object param, Integer firstResult, Integer maxResult);

    /**
     * 查询所有
     *
     * @return
     */
    List<T> findAll();

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    T findById(Serializable id);

    /**
     * 更新
     *
     * @param t
     */
    void update(T t);

    /**
     * 删除
     *
     * @param t
     */
    void delete(T t);
}
