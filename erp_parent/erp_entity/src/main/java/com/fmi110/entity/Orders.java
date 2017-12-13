package com.fmi110.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/9.
 */
@Entity
@SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Orders {
    /**
     * 类型为 :采购
     */
    @Transient
    public static final String TYPE_IN      = "1";
    /**
     * 类型为 :销售
     */
    @Transient
    public static final String TYPE_OUT     = "2";
    @Transient
    public static final String STATE_CREATE = "0"; // 未审核
    @Transient
    public static final String STATE_CHECK  = "1"; // 已审核
    @Transient
    public static final String STATE_START  = "2"; // 已确认
    @Transient
    public static final String STATE_IN        = "3";//已入库

    private Long   uuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   checktime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   starttime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   endtime;
    private String type;
    //    private Long creater;
    private Emp    creater;
    //    private Long checker;
    private Emp    checker;
    //    private Long starter;
    private Emp    starter;
    private Emp    ender;
//    private Long   ender;
    //    private Long supplieruuid;
    private Double totalmoney;
    private String state; // 采购: 0 未审核 1 已审核 2 已确认 3 已入库;  销售 : 0 未出库  1 已出库
    private Long   waybillsn;

    private Supplier          supplier;
    //    @JsonIgnore
    private List<Orderdetail> orderdetails;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "CHECKTIME")
    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    @Basic
    @Column(name = "STARTTIME")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
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
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    @Basic
//    @Column(name = "CREATER")
//    public Long getCreater() {
//        return creater;
//    }
//
//    public void setCreater(Long creater) {
//        this.creater = creater;
//    }

    @OneToOne
    @JoinColumn(name = "CREATER")
    public Emp getCreater() {
        return creater;
    }

    public void setCreater(Emp creater) {
        this.creater = creater;
    }

    //    @Basic
//    @Column(name = "CHECKER")
//    public Long getChecker() {
//        return checker;
//    }
//
//    public void setChecker(Long checker) {
//        this.checker = checker;
//    }
    @OneToOne
    @JoinColumn(name = "CHECKER")
    public Emp getChecker() {
        return checker;
    }

    public void setChecker(Emp checker) {
        this.checker = checker;
    }

//    @Basic
//    @Column(name = "STARTER")
//    public Long getStarter() {
//        return starter;
//    }
//
//    public void setStarter(Long starter) {
//        this.starter = starter;
//    }

    @ManyToOne
    @JoinColumn(name = "STARTER")
    public Emp getStarter() {
        return starter;
    }

    public void setStarter(Emp starter) {
        this.starter = starter;
    }

//    @Basic
//    @Column(name = "ENDER")
//    public Long getEnder() {
//        return ender;
//    }
//
//    public void setEnder(Long ender) {
//        this.ender = ender;
//    }

    @ManyToOne
    @JoinColumn(name = "ENDER")
    public Emp getEnder() {
        return ender;
    }

    public void setEnder(Emp ender) {
        this.ender = ender;
    }

    @Basic
    @Column(name = "TOTALMONEY")
    public Double getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(Double totalmoney) {
        this.totalmoney = totalmoney;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "WAYBILLSN")
    public Long getWaybillsn() {
        return waybillsn;
    }

    public void setWaybillsn(Long waybillsn) {
        this.waybillsn = waybillsn;
    }

//    @Column(name = "supplieruuid")
//    public Long getSupplieruuid() {
//        return supplieruuid;
//    }
//
//    public void setSupplieruuid(Long supplieruuid) {
//        this.supplieruuid = supplieruuid;
//    }

    @ManyToOne
    @JoinColumn(name = "SUPPLIERUUID")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL) // 设置级联保存和更新,删除!!!
    public List<Orderdetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }
}
