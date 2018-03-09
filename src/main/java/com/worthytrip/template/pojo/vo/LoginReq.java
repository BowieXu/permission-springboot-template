package com.worthytrip.template.pojo.vo;

import com.worthytrip.template.pojo.BaseReq;

/**
 * Created by xubin on 2018/3/7.
 */
public class LoginReq extends BaseReq {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
