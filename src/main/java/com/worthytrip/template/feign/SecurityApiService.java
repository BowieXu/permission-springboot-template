package com.worthytrip.template.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worthytrip.security.common.model.constant.Constants;
import com.worthytrip.security.common.model.permission.PermissionValidateRequest;
import com.worthytrip.security.common.model.permission.PermissionValidateResponse;
import com.worthytrip.security.common.model.permission.PermissionsRequest;
import com.worthytrip.security.common.model.permission.PermissionsResponse;
import com.worthytrip.security.common.model.user.BaseResponse;
import com.worthytrip.security.common.model.user.BaseTokenRequest;
import com.worthytrip.security.common.model.user.LoginRequest;
import com.worthytrip.security.common.model.user.LoginResponse;
import com.worthytrip.security.common.model.user.LogoutRequest;
import com.worthytrip.security.common.model.user.LogoutResponse;
import com.worthytrip.security.common.model.user.UserValidateRequest;
import com.worthytrip.security.common.model.user.UserValidateResponse;

/**
 * Created by xubin on 2018/3/7.
 */
@FeignClient(value = Constants.SERVICE_SECURITY_API_SERVICE_NAME)
public interface SecurityApiService {
    @RequestMapping(value = Constants.URI_USER_LOGIN, method = RequestMethod.POST)
    LoginResponse login(@RequestBody LoginRequest body);

    @RequestMapping(value = Constants.URI_USER_LOGOUT, method = RequestMethod.POST)
    LogoutResponse logout(@RequestBody LogoutRequest body);

    @RequestMapping(value = Constants.URI_USER_VALIDATE, method = RequestMethod.POST)
    UserValidateResponse userValidate(@RequestBody UserValidateRequest body);

    @RequestMapping(value = Constants.URI_TOKEN_VALIDATE, method = RequestMethod.POST)
    BaseResponse tokenValidate(@RequestBody BaseTokenRequest body);

    @RequestMapping(value = Constants.URI_SERVICE_VALIDATE, method = RequestMethod.GET)
    String serviceValidate();

    @RequestMapping(value = Constants.URI_PERMISSION_VALIDATE, method = RequestMethod.POST)
    PermissionValidateResponse permissionValidate(@RequestBody PermissionValidateRequest body);

    @RequestMapping(value = Constants.URI_PERMISSIONS, method = RequestMethod.POST)
    PermissionsResponse findPermissions(@RequestBody PermissionsRequest body);
}
