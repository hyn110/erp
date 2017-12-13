package com.fmi110.dao;

import com.fmi110.entity.Dep;

import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
public interface DepDao extends BaseDao<Dep>{
    /**
     * 按条件分页查找
     * @param dep
     * @param firstResult
     * @param maxResult
     * @return
     */
    List<Dep> findList(Dep dep,Integer firstResult,Integer maxResult) ;
}
