package com.fmi110.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangyunning on 2017/12/10.
 */
@Entity
public class Storeoper {
    /**入库*/
    @Transient
    public static final String IN = "1";
    /**出库*/
    @Transient
    public static final String OUT = "2";
    private Long uuid;
//    private Long empuuid;
    private Emp emp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opertime;
//    private Long storeuuid;
//    private Long goodsuuid;
    private Store store;
    private Goods goods;
    private Long num;
    private String type;

    @Id
    @Column(name = "UUID")
    @SequenceGenerator(name = "STOREOPER_SEQ",sequenceName = "STOREOPER_SEQ",
                       initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "STOREOPER_SEQ")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }


    @ManyToOne
    @JoinColumn(name="EMPUUID")
    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    @Basic
    @Column(name = "OPERTIME")
    public Date getOpertime() {
        return opertime;
    }

    public void setOpertime(Date opertime) {
        this.opertime = opertime;
    }

//
    @ManyToOne
    @JoinColumn(name="STOREUUID")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    @ManyToOne
    @JoinColumn(name="GOODSUUID")
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

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
