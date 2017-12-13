package com.fmi110.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangyunning on 2017/12/9.
 */
@Entity
@SequenceGenerator(name = "ORDERDETAIL_SEQ", sequenceName = "ORDERDETAIL_SEQ", initialValue = 1, allocationSize = 1)
public class Orderdetail {
    /**
     * 未入库
     */
    @Transient
    public static final String STATE_NOT_IN = "0";
    /**已入库*/
    @Transient
    public static final String STATE_IN        = "1";
    private Long uuid;
    private Long goodsuuid;
    private Goods goods;
    private String goodsname;
    private Double price;
    private Long num;
    private Double money;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endtime;
//    private Long ender;
//    private Long storeuuid;
    private Emp ender;
    private Store store;

    private String state;
    /**
     * 用于返回给前端的字段,不与数据库产生映射
     */

    private Long ordersuuid;
    @JsonIgnore
    private Orders order;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ORDERDETAIL_SEQ")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }


    @Basic
    @Column(name = "GOODSNAME")
    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    @Basic
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
    @Column(name = "MONEY")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "ENDTIME")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "ORDERSUUID")
//    @Cascade(CascadeType.REMOVE)
    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
        this.ordersuuid = order.getUuid();
    }
    @Transient
    public Long getGoodsuuid() {
        return goodsuuid;
    }

    public void setGoodsuuid(Long goodsuuid) {
        this.goodsuuid = goodsuuid;
        if(goods==null){
            goods = new Goods();
        }
        System.out.println("=======接收前端的goodsuuid,手动赋值给关联对象goods");
        goods.setUuid(goodsuuid);
    }

    @ManyToOne
    @JoinColumn(name = "ENDER")
    public Emp getEnder() {
        return ender;
    }

    public void setEnder(Emp ender) {
        this.ender = ender;
    }
    @ManyToOne
    @JoinColumn(name = "STOREUUID")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    @Transient
    public Long getOrdersuuid() {
        return ordersuuid;
    }

    public void setOrdersuuid(Long ordersuuid) {
        this.ordersuuid = ordersuuid;
    }
    @ManyToOne
    @JoinColumn(name = "GOODSUUID")
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
