package com.xunyuc.xproject.web.dao;

import com.xunyuc.xproject.web.bean.po.User;
import com.xunyuc.xproject.web.bean.proxy.UserProxy;
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

    public UserProxy findUserProxyByName(String[] fields, String name) {
        StringBuffer sql = new StringBuffer();
        if(fields == null || fields.length == 0){
            return null;
        }
        sql.append("select ");
        sql.append(fields[0]);
        for(int i = 1;i < fields.length;i++){
            sql.append(",");
            sql.append(fields[i]);
        }
        sql.append(" from user where name = (:name) ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        User user = this.namedQueryForObject(sql.toString(), User.class, paramMap);
        return new UserProxy(fields, user);
    }

}
