package com.fmi110.entity;

import javax.persistence.*;

/**
 * Created by huangyunning on 2017/12/8.
 */
@Entity
@SequenceGenerator(name = "SUPPLIER_SEQ",sequenceName = "SUPPLIER_SEQ",initialValue = 1,allocationSize = 1)
public class Supplier {
    private Long uuid;
    private String name;
    private String address;
    private String contact;
    private String tele;
    private String email;
    private String type;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SUPPLIER_SEQ")
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
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "CONTACT")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "TELE")
    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int result = (int) (uuid ^ (uuid >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (tele != null ? tele.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        if (uuid != supplier.uuid) return false;
        if (name != null ? !name.equals(supplier.name) : supplier.name != null) return false;
        if (address != null ? !address.equals(supplier.address) : supplier.address != null) return false;
        if (contact != null ? !contact.equals(supplier.contact) : supplier.contact != null) return false;
        if (tele != null ? !tele.equals(supplier.tele) : supplier.tele != null) return false;
        if (email != null ? !email.equals(supplier.email) : supplier.email != null) return false;
        if (type != null ? !type.equals(supplier.type) : supplier.type != null) return false;

        return true;
    }
}
