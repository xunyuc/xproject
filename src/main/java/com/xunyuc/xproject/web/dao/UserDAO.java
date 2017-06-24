package com.xunyuc.xproject.web.dao;

import com.xunyuc.xproject.web.bean.po.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@Repository
public class UserDAO extends BaseDAO {

    private static String findSql = "select * from user where name = (:name)";

    public User findUserByName(String name) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        User user = this.namedQueryForObject(findSql, User.class, paramMap);
        return user;
    }

}
