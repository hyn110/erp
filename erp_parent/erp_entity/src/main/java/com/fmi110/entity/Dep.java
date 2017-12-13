package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Entity
@SequenceGenerator(name = "dep_seq",sequenceName = "DEP_SEQ",initialValue = 1,allocationSize = 1)
public class Dep {
    private Long uuid;
    private String name;
    private String tele;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "dep_seq")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TELE")
    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    @Override
    public int hashCode() {
        int result = (int) (uuid ^ (uuid >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tele != null ? tele.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dep dep = (Dep) o;

        if (uuid != dep.uuid) return false;
        if (name != null ? !name.equals(dep.name) : dep.name != null) return false;
        if (tele != null ? !tele.equals(dep.tele) : dep.tele != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Dep{" +
               "uuid=" + uuid +
               ", name='" + name + '\'' +
               ", tele='" + tele + '\'' +
               '}';
    }
}
