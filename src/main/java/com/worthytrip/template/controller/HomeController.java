package com.worthytrip.template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worthytrip.template.constant.ConstantCustom;
import com.worthytrip.template.pojo.BaseReq;
import com.worthytrip.template.pojo.BaseResp;
import com.worthytrip.template.pojo.airfront.User;
import com.worthytrip.template.service.HomeService;

/**
 * Created by xubin on 2018/3/2.
 */
@RestController
@RequestMapping(value = "test")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @RequestMapping("/test1")
    @ResponseBody
    public Object home(@RequestBody BaseReq baseReq) {
        List<User> userList = homeService.getUser();
        BaseResp baseResp = new BaseResp(ConstantCustom.RESP_STATUS_SUCCESS, ConstantCustom.MESSAGE_SUCCESS);
        baseResp.setData(userList);
        return baseResp;
    }
}
