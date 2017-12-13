package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/6.
 */
@Entity
@SequenceGenerator(name = "GOODSTYPE_SEQ",sequenceName = "GOODSTYPE_SEQ",initialValue = 1,allocationSize = 1)
public class GoodsType {
    private Long uuid;
    private String name;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(generator = "GOODSTYPE_SEQ")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
