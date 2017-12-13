package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/10.
 */
@Entity
@SequenceGenerator(name = "STOREDETAIL_SEQ",sequenceName = "STOREDETAIL_SEQ",initialValue = 1,allocationSize = 1)
public class Storedetail {
    private long  uuid;
    //    private Long storeuuid;
//    private Long goodsuuid;
    private Store store;
    private Goods goods;
    private Long  num;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "STOREDETAIL_SEQ")
    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }


    @ManyToOne
    @JoinColumn(name = "STOREUUID")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    @ManyToOne
    @JoinColumn(name = "GOODSUUID")
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Basic
    @Column(name = "NUM")
    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

}
