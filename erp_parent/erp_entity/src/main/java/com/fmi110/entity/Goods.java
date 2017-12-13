package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/6.
 */
@Entity
@SequenceGenerator(name = "GOODS_SEQ",sequenceName = "GOODS_SEQ",initialValue = 1,allocationSize = 1)
public class Goods {
    private Long      uuid;
    private String    name;
    private String    origin;
    private String    producer;
    private String    unit;
    private Double      inprice;
    private Double      outprice;
//    private Long goodstypeuuid;
    private GoodsType goodsType;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "GOODS_SEQ")
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

    @Basic
    @Column(name = "ORIGIN")
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "PRODUCER")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "INPRICE")
    public Double getInprice() {
        return inprice;
    }

    public void setInprice(Double inprice) {
        this.inprice = inprice;
    }

    @Basic
    @Column(name = "OUTPRICE")
    public Double getOutprice() {
        return outprice;
    }

    public void setOutprice(Double outprice) {
        this.outprice = outprice;
    }
//    @Transient
    @ManyToOne
    @JoinColumn(name = "GOODSTYPEUUID")
    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }
}
