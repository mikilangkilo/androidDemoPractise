package com.example.administrator.customchat.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by yinpengcheng on 2017/10/12.
 */

public class User extends BmobObject {
    String name;
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
