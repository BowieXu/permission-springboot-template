package com.worthytrip.template.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.template.constant.ConstantCustom;
import com.worthytrip.template.pojo.BaseResp;
import com.worthytrip.template.pojo.vo.InfoReq;
import com.worthytrip.template.pojo.vo.InfoResp;
import com.worthytrip.template.pojo.vo.LoginReq;
import com.worthytrip.template.pojo.vo.LoginResp;
import com.worthytrip.template.pojo.vo.LogoutReq;
import com.worthytrip.template.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(@RequestBody LoginReq loginReq) {
        LoginResp loginResp = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("login request:{}", JSONObject.toJSONString(loginReq));
            }
            loginResp = userService.login(loginReq);
        } catch (Exception e) {
            logger.error("login error.[{}]", JSONObject.toJSONString(loginReq), e);
            loginResp = new LoginResp(ConstantCustom.RESP_STATUS_FAIL_ONE, "login error.[" + e.getMessage() + "]");
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("login response:{}", JSONObject.toJSONString(loginResp));
            }
        }
        return loginResp;
    }

    @ResponseBody
    @RequestMapping(value = "/info")
    public Object info(@RequestBody InfoReq infoReq) {
        InfoResp infoResp = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("info request:{}", JSONObject.toJSONString(infoReq));
            }
            infoResp = userService.info(infoReq);
        } catch (Exception e) {
            logger.error("info error.[{}]", JSONObject.toJSONString(infoReq), e);
            infoResp = new InfoResp(ConstantCustom.RESP_STATUS_FAIL_ONE, "login error.[" + e.getMessage() + "]");
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("info response:{}", JSONObject.toJSONString(infoResp));
            }
        }
        return infoResp;
    }

    @ResponseBody
    @RequestMapping(value = "logout")
    public Object logout(@RequestBody LogoutReq logoutReq) {
        BaseResp baseResp = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("logout request:{}", JSONObject.toJSONString(logoutReq));
            }
            baseResp = userService.logout(logoutReq);
        } catch (Exception e) {
            logger.error("logout error.[{}]", JSONObject.toJSONString(logoutReq), e);
            baseResp = new BaseResp(ConstantCustom.RESP_STATUS_FAIL_ONE, "login error.[" + e.getMessage() + "]");
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("logout response:{}", JSONObject.toJSONString(baseResp));
            }
        }
        return baseResp;
    }
}