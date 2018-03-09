package com.worthytrip.template.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.security.common.model.permission.PermissionsRequest;
import com.worthytrip.security.common.model.permission.PermissionsResponse;
import com.worthytrip.security.common.model.user.LoginRequest;
import com.worthytrip.security.common.model.user.LoginResponse;
import com.worthytrip.security.common.model.user.LogoutRequest;
import com.worthytrip.security.common.model.user.LogoutResponse;
import com.worthytrip.template.constant.ConstantCustom;
import com.worthytrip.template.feign.SecurityApiService;
import com.worthytrip.template.init.InitConfigUtil;
import com.worthytrip.template.pojo.BaseResp;
import com.worthytrip.template.pojo.vo.InfoReq;
import com.worthytrip.template.pojo.vo.InfoResp;
import com.worthytrip.template.pojo.vo.LoginReq;
import com.worthytrip.template.pojo.vo.LoginResp;
import com.worthytrip.template.pojo.vo.LogoutReq;
import com.worthytrip.template.service.UserService;

/**
 * Created by xubin on 2018/3/7.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InitConfigUtil initConfigUtil;
    @Autowired
    private SecurityApiService securityApiService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public LoginResp login(LoginReq loginReq) {
        if (loginReq == null || StringUtils.isBlank(loginReq.getUsername())
                || StringUtils.isBlank(loginReq.getPassword())) {
            return new LoginResp(ConstantCustom.RESP_STATUS_FAIL_ONE, "用户名/密码为空.");
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAppCode(initConfigUtil.getAppCode());
        loginRequest.setUsername(loginReq.getUsername());
        loginRequest.setPassword(loginReq.getPassword());
        if (logger.isDebugEnabled()) {
            logger.debug("securityApiService login request:{}", JSONObject.toJSONString(loginRequest));
        }
        LoginResponse loginResponse = securityApiService.login(loginRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("securityApiService login response:{}", JSONObject.toJSONString(loginResponse));
        }
        if (loginResponse.getStatus() != ConstantCustom.INT_ZERO) {
            return new LoginResp(loginResponse.getStatus(), loginResponse.getMessage());
        }
        LoginResp loginResp = new LoginResp(ConstantCustom.RESP_STATUS_SUCCESS, ConstantCustom.RESP_MESSAGE_SUCCESS);
        loginResp.setUsername(loginReq.getUsername());
        loginResp.setToken(loginResponse.getToken());
        loginResp.setNickname(loginResponse.getNickname());
        loginResp.setSuperFlag(loginResponse.getSflag());
        return loginResp;
    }

    public BaseResp logout(LogoutReq logoutReq) {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAppCode(initConfigUtil.getAppCode());
        logoutRequest.setUsername(logoutReq.getUsername());
        logoutRequest.setToken(httpServletRequest.getHeader(ConstantCustom.HEADER_TOKEN));
        if (logger.isDebugEnabled()) {
            logger.debug("securityApiService logout request:{}", JSONObject.toJSONString(logoutRequest));
        }
        LogoutResponse logoutResponse = securityApiService.logout(logoutRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("securityApiService logout response:{}", JSONObject.toJSONString(logoutResponse));
        }
        if (logoutResponse.getStatus() != ConstantCustom.INT_ZERO) {
            return new BaseResp(logoutResponse.getStatus(), logoutResponse.getMessage());
        }
        return new BaseResp(ConstantCustom.RESP_STATUS_SUCCESS, ConstantCustom.RESP_MESSAGE_SUCCESS);
    }

    public InfoResp info(InfoReq infoReq) {
        PermissionsRequest pmRequest = new PermissionsRequest();
        pmRequest.setAppCode(initConfigUtil.getAppCode());
        pmRequest.setUsername(infoReq.getUsername());
        pmRequest.setToken(httpServletRequest.getHeader(ConstantCustom.HEADER_TOKEN));
        PermissionsResponse pmResponse = securityApiService.findPermissions(pmRequest);
        if (pmResponse.getStatus() != ConstantCustom.INT_ZERO) {
            return new InfoResp(ConstantCustom.RESP_STATUS_FAIL_ONE, pmResponse.getMessage());
        }
        return new InfoResp(ConstantCustom.RESP_STATUS_SUCCESS, pmResponse.getMessage(), infoReq.getUsername(),
                pmResponse.getPermissions());
    }
}
