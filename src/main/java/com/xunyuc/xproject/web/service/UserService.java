package com.xunyuc.xproject.web.service;

import com.xunyuc.xproject.web.dao.IUserInfoDAO;
import com.xunyuc.xproject.web.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@Service
public class UserService {

    @Resource(name = "mybatisUserInfoDAO")
    private IUserInfoDAO userInfoDAO;

    public UserInfo findUserByName(String name) {
        return userInfoDAO.findUserByName(name);
    }

//    public UserProxy findUserProxyByName2(EnumSet<UserProxy.Field> fields, String name) {
//        return userInfoDAO.findUserProxyByName2(fields, name);
//    }

}