package com.xunyuc.xproject.web.service;

import com.xunyuc.xproject.web.bean.po.User;
import com.xunyuc.xproject.web.bean.proxy.UserProxy;
import com.xunyuc.xproject.web.bean.proxy.UserProxy2;
import com.xunyuc.xproject.web.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User findUserByName(String name) {
        return userDAO.findUserByName(name);
    }

    public UserProxy findUserProxyByName(String[] fields, String name) {
        return userDAO.findUserProxyByName(fields, name);
    }

    public UserProxy2 findUserProxyByName2(EnumSet<UserProxy2.Field> fields, String name) {
        return userDAO.findUserProxyByName2(fields, name);
    }

}