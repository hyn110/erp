package com.fmi110.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/7.
 */
@Entity
public class Menu {
    private String    menuid;
    private String    menuname;
    private String    icon;
    private String    url;
    private String pid;
    private List<Menu> menus; // 下级菜单
//    private Set<Menu> menus;

    @Id
    @Column(name = "MENUID")
    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    @Basic
    @Column(name = "MENUNAME")
    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    @Basic
    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToMany
    @JoinColumn(name = "PID")
    @OrderBy("menuid")
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

//    @OneToMany
//    @JoinColumn(name = "PID")
//    @OrderBy("menuid")
//    public Set<Menu> getMenus() {
//        return menus;
//    }
//
//    public void setMenus(Set<Menu> menus) {
//        this.menus = menus;
//    }

    @Override
    public String toString() {
        return "Menu{" +
               "menuid='" + menuid + '\'' +
               ", url='" + url + '\'' +
               ", menus=" + menus +
               '}';
    }
}
