package com.fmi110.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by huangyunning on 2017/12/14.
 */
@Entity
@Table(name = "VIEW_STOREALERT")
public class StoreAlert {
    @Id
    private Long uuid;
    @Column
    private String name;
    @Column
    private Long storenum;
    @Column
    private Long outnum;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStorenum() {
        return storenum;
    }

    public void setStorenum(Long storenum) {
        this.storenum = storenum;
    }

    public Long getOutnum() {
        return outnum;
    }

    public void setOutnum(Long outnum) {
        this.outnum = outnum;
    }

    @Override
    public String toString() {
        return "StoreAlert{" +
               "uuid=" + uuid +
               ", name='" + name + '\'' +
               ", storenum=" + storenum +
               ", outnum=" + outnum +
               '}';
    }
}
