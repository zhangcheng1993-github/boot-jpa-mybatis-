package com.base.springboot.entity;


import com.base.springboot.common.config.SystemConstant;
import com.base.springboot.common.macb.jpaHandle.ObjectQueryImpl;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "T_USER")
public class User extends ObjectQueryImpl<User,Integer> implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "PK_ID",nullable = false,columnDefinition = SystemConstant.INT+"(11) default 0 comment '主键,自增'")
    private  Integer id;

    @Column(name = "NAME",nullable = false,columnDefinition = SystemConstant.VARCHAR+"(100) default '' comment '名称'")
    private String name;

    @Column(name = "AGE",nullable = false,columnDefinition = SystemConstant.INT+"(11) default 1 comment '年龄'")
    private Integer age;

    @Column(name = "ADD_DATE",nullable = false,columnDefinition = SystemConstant.DATE+" comment '新增时间'")
    private Date addDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
