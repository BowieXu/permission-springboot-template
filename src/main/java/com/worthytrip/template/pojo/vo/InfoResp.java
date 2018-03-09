package com.worthytrip.template.pojo.vo;

import java.util.List;

import com.worthytrip.security.common.model.permission.BasePermission;
import com.worthytrip.template.pojo.BaseResp;

/**
 * Created by xubin on 2018/3/7.
 */
public class InfoResp extends BaseResp {
    private String username;
    private List<BasePermission> permissions;

    public InfoResp() {

    }

    public InfoResp(Integer status, String message) {
        super(status, message);
    }

    public InfoResp(Integer status, String message, String username, List<BasePermission> permissions) {
        super(status, message);
        this.username = username;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BasePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<BasePermission> permissions) {
        this.permissions = permissions;
    }
}
