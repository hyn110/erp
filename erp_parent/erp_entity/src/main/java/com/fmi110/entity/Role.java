package com.fmi110.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/21.
 */
@Entity
@SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
public class Role {
    private Long uuid;
    private String name;
    @JsonIgnore
    private List<Menu> menus;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ROLE_SEQ")
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

    @Override
    public int hashCode() {
        int result = (int) (uuid ^ (uuid >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("menus")
    @JoinTable(name="ROLE_MENU",
               joinColumns = {@JoinColumn(name = "ROLEUUID",referencedColumnName = "UUID")},
                inverseJoinColumns = {@JoinColumn(name="MENUUUID",referencedColumnName = "MENUID")}
    )
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (uuid != role.uuid) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }
}
