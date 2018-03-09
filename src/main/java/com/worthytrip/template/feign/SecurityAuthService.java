package com.worthytrip.template.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worthytrip.security.common.model.constant.Constants;
import com.worthytrip.security.common.model.user.BaseResponse;
import com.worthytrip.security.common.model.user.CacheInfo;
import com.worthytrip.security.common.model.user.CacheResponse;
import com.worthytrip.security.common.model.user.LoginResponse;
import com.worthytrip.security.common.model.user.UserInfo;

/**
 * Created by xubin on 2018/3/7.
 */
@FeignClient(value = Constants.SERVICE_SECURITY_AUTH_SERVICE_NAME)
public interface SecurityAuthService {
    @RequestMapping(value = Constants.URI_AUTH_VALIDATE_ACTION, method = RequestMethod.POST)
    BaseResponse validate(@PathVariable(value = "action") String action, @RequestBody String body);

    @RequestMapping(value = Constants.URI_AUTH_USER, method = RequestMethod.PUT)
    LoginResponse saveUserToCache(@RequestBody UserInfo body);

    @RequestMapping(value = Constants.URI_AUTH_USER, method = RequestMethod.POST)
    BaseResponse updateUserToCache(@RequestBody CacheInfo cacheInfo);

    @RequestMapping(value = Constants.URI_AUTH_USER_KEY, method = RequestMethod.GET)
    CacheResponse getUserFromCache(@PathVariable("key") String key);

    @RequestMapping(value = Constants.URI_AUTH_USER_KEY, method = RequestMethod.DELETE)
    BaseResponse deleteUserFromCache(@PathVariable("key") String key);
}
