package com.worthytrip.template.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.worthytrip.security.common.model.constant.Constants;
import com.worthytrip.security.common.model.user.BaseResponse;
import com.worthytrip.security.common.model.user.BaseTokenRequest;
import com.worthytrip.security.common.model.user.UserValidateRequest;
import com.worthytrip.security.common.model.user.UserValidateResponse;
import com.worthytrip.template.constant.ConstantCustom;
import com.worthytrip.template.feign.SecurityApiService;
import com.worthytrip.template.init.InitConfigUtil;
import com.worthytrip.template.pojo.BaseResp;

/**
 * Created by xubin on 2018/3/7.
 */
@Aspect
@Component
public class PermissionAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitConfigUtil initConfigUtil;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private SecurityApiService securityApiService;

    @Pointcut(value = "execution(public * com.worthytrip.template.controller.*.*(..)) ")
    public void verify() {

    }

    @Around("verify()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            String url = httpServletRequest.getRequestURI();
            if (StringUtils.isNotBlank(initConfigUtil.getAllWhitePath())
                    && initConfigUtil.getAllWhitePath().contains(url)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("url in whitePath:{}", url);
                }
                return proceedingJoinPoint.proceed();
            }

            String appCode = initConfigUtil.getAppCode();
            String token = httpServletRequest.getHeader(ConstantCustom.HEADER_TOKEN);
            String username = null;
            if (proceedingJoinPoint.getArgs() != null) {
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(proceedingJoinPoint.getArgs()[0]));
                username = json.getString("username");
            }
            if (StringUtils.isNotBlank(initConfigUtil.getPermissionWhitePath())
                    && initConfigUtil.getPermissionWhitePath().contains(url)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("url in permissionWhitePath:{}", url);
                }
                BaseTokenRequest tokenRequest = new BaseTokenRequest();
                tokenRequest.setAppCode(appCode);
                tokenRequest.setUsername(username);
                tokenRequest.setToken(token);
                if (logger.isDebugEnabled()) {
                    logger.debug("tokenValidate request:{}", JSONObject.toJSONString(tokenRequest));
                }
                BaseResponse tokenValidResponse = securityApiService.tokenValidate(tokenRequest);
                if (logger.isDebugEnabled()) {
                    logger.debug("tokenValidate response:{}", JSONObject.toJSONString(tokenValidResponse));
                }
                if (tokenValidResponse == null || tokenValidResponse.getStatus() != ConstantCustom.INT_ZERO) {
                    return new BaseResp(tokenValidResponse.getStatus(), tokenValidResponse.getMessage());
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("url in permission:{}", url);
                }
                UserValidateRequest userValidateRequest = new UserValidateRequest();
                userValidateRequest.setAppCode(appCode);
                userValidateRequest.setUsername(username);
                userValidateRequest.setToken(token);
                userValidateRequest.setPermissionData(url);
                if (logger.isDebugEnabled()) {
                    logger.debug("userValidate request:{}", JSONObject.toJSONString(userValidateRequest));
                }
                UserValidateResponse userValidateResponse = securityApiService.userValidate(userValidateRequest);
                if (logger.isDebugEnabled()) {
                    logger.debug("userValidate response:{}", JSONObject.toJSONString(userValidateResponse));
                }
                if (userValidateResponse == null || userValidateResponse.getStatus() != ConstantCustom.INT_ZERO) {
                    return new BaseResp(userValidateResponse.getStatus(), userValidateResponse.getMessage());
                }
            }
            // CacheInfo cacheInfo = new CacheInfo();
            // cacheInfo.setKey(getUserRedisKey(username, appCode, token));
            // if (logger.isDebugEnabled()) {
            // logger.debug("updateUserToCache request:{}",
            // JSONObject.toJSONString(cacheInfo));
            // }
            // BaseResponse baseResponse = securityAuthService.updateUserToCache(cacheInfo);
            // if (logger.isDebugEnabled()) {
            // logger.debug("updateUserToCache response:{}",
            // JSONObject.toJSONString(baseResponse));
            // }
        } catch (Exception e) {
            logger.error("PermissionAspect doAround error.", e);
            return new BaseResp(ConstantCustom.STATUS_EXCEPTION, "权限验证异常！");
        }
        return proceedingJoinPoint.proceed();
    }

    private String getSessionIdByToken(String token) {
        String sessionId = Constants.BLANK;
        String[] jwtTokenArr = token.split(Constants.POINT_SPLIT, -1);
        if (jwtTokenArr.length == 4) {
            sessionId = jwtTokenArr[3];
        }
        return sessionId;
    }

    private String getUserRedisKey(String userName, String appCode, String token) {
        String sessionId = getSessionIdByToken(token);
        return userName + sessionId + Constants.HYPHEN + appCode;
    }
}
