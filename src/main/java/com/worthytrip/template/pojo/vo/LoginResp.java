package com.worthytrip.template.pojo.vo;

import com.worthytrip.template.pojo.BaseResp;

/**
 * Created by xubin on 2018/3/7.
 */
public class LoginResp extends BaseResp {
    private String username;
    private String token;
    private String nickname;
    private String superFlag;
    private Integer userId;

    public LoginResp() {

    }

    public LoginResp(Integer status, String message) {
        super(status, message);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSuperFlag() {
        return superFlag;
    }

    public void setSuperFlag(String superFlag) {
        this.superFlag = superFlag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
