package com.clover.entities;

import cn.bmob.im.bean.BmobChatUser;

public class User extends BmobChatUser{

    private static final long serialVersionUID = 1L;

    private Integer age = 18;

    private Boolean sex;    //男-false-女-true

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
