package com.fmi110.dao;

import java.io.Serializable;
import java.util.List;

/**
 * BaseDao 定义了常用的查询方法
 *
 * @param <T>
 */
public interface BaseDao<T> {
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

    long findCount(T t1, T t2, Object param);

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
     * @return
     */
    List<T> findPage(T t1, T t2, Object param, Integer firstResult, Integer maxResult);

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
