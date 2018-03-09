package com.worthytrip.template.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worthytrip.template.mapper.airfront.UserMapper;
import com.worthytrip.template.pojo.airfront.User;
import com.worthytrip.template.service.HomeService;

/**
 * Created by xubin on 2018/3/2.
 */
@Service(value = "homeService")
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getUser() {
        return userMapper.selectByExample(null);
    }
}
