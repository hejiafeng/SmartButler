package com.imooc.smartbutler.entity;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.entity
* 文件名： MyUser
* 创建者： HJF
* 创建时间： 2017/5/1321:05
* 描述： 用户属性
*
*/

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser{
    private int age;
    private  boolean gender;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
