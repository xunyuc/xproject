package com.xunyuc.xproject.web.dao;

import com.xunyuc.xproject.web.annotation.MybatisRepository;
import com.xunyuc.xproject.web.entity.UserInfo;
import org.springframework.stereotype.Repository;

@MybatisRepository("mybatisUserInfoDAO")
public interface IUserInfoDAO {

    UserInfo findUserByName(String name);
}