package com.worthytrip.template.service;

import com.worthytrip.template.pojo.BaseResp;
import com.worthytrip.template.pojo.vo.InfoReq;
import com.worthytrip.template.pojo.vo.InfoResp;
import com.worthytrip.template.pojo.vo.LoginReq;
import com.worthytrip.template.pojo.vo.LoginResp;
import com.worthytrip.template.pojo.vo.LogoutReq;

/**
 * Created by xubin on 2018/3/7.
 */
public interface UserService{
    LoginResp login(LoginReq loginReq);

    BaseResp logout(LogoutReq logoutReq);

    InfoResp info(InfoReq infoReq);
}
