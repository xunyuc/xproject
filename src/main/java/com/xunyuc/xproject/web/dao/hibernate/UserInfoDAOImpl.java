package com.xunyuc.xproject.web.dao.hibernate;

import com.xunyuc.xproject.web.dao.IUserInfoDAO;
import com.xunyuc.xproject.web.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by Xunyuc on 2017/7/8.
 */
@Repository("hibernateUserInfoDAO")
public class UserInfoDAOImpl extends BaseDAO<UserInfo, String> implements IUserInfoDAO{
    public UserInfo findUserByName(String name) {
        return this.getSession().createQuery(" from UserInfo where name = :name ", UserInfo.class)
                .setParameter("name", name).getSingleResult();
    }
}
