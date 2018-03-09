package com.worthytrip.template.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by xubin on 2018/3/7.
 */
@Component
@Configuration
public class InitConfigUtil {
    @Value("${app.code}")
    private String appCode;
    @Value("${app.permission.all-white}")
    private String allWhitePath;
    @Value("${app.permission.permission-white}")
    private String permissionWhitePath;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAllWhitePath() {
        return allWhitePath;
    }

    public void setAllWhitePath(String allWhitePath) {
        this.allWhitePath = allWhitePath;
    }

    public String getPermissionWhitePath() {
        return permissionWhitePath;
    }

    public void setPermissionWhitePath(String permissionWhitePath) {
        this.permissionWhitePath = permissionWhitePath;
    }
}
