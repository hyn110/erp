package com.fmi110.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/4.
 */
@Entity
@SequenceGenerator(name = "EMP_SEQ",sequenceName = "EMP_SEQ",initialValue = 1,allocationSize = 1)
@DynamicUpdate
public class Emp {
    private Long   uuid;
    private String username;
    @JsonIgnore
    private String pwd;
    private String name;
    private Long   gender;
    private String email;
    private String tele;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // springMVC 接收Date类型数据转换格式
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")   // jackson 序列化日期输出的格式
    private Date   birthday;
//    private Long depuuid;
    private Dep    dep;

    private List<Role> roles;

    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "EMP_SEQ")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PWD")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "GENDER")
    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "BIRTHDAY")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

//    @Basic
//    @Column(name = "DEPUUID")
//    public Long getDepuuid() {
//        return depuuid;
//    }
//
//    public void setDepuuid(Long depuuid) {
//        this.depuuid = depuuid;
//    }

    @JoinColumn(name ="DEPUUID")
    @ManyToOne
//    @Cascade(CascadeType.SAVE_UPDATE) // hibernate 的注解!!!
    public Dep getDep() {
        return dep;
    }

    public void setDep(Dep dep) {
        this.dep = dep;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("uuid")  // 填 Roles类的属性
    @JoinTable(name="EMP_ROLE",
               joinColumns = {@JoinColumn(name = "EMPUUID",referencedColumnName = "UUID")},
               inverseJoinColumns = {@JoinColumn(name="ROLEUUID",referencedColumnName = "UUID")})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Emp{" +
               "uuid=" + uuid +
               ", username='" + username + '\'' +
               ", pwd='" + pwd + '\'' +
               ", name='" + name + '\'' +
               ", birthday=" + birthday +
               ", depuuid=" + dep +
               '}';
    }
}
