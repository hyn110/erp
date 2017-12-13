package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/10.
 */
@Entity
@SequenceGenerator(name = "STORE_SEQ",sequenceName = "STORE_SEQ",initialValue = 1,allocationSize = 1)
public class Store {
    private Long uuid;
    private String name;
    private Emp emp;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_SEQ")
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

    @ManyToOne
    @JoinColumn(name="EMPUUID")
    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }


}
