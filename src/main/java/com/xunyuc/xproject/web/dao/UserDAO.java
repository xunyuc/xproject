package com.xunyuc.xproject.web.dao;

import com.xunyuc.xproject.web.bean.po.User;
import com.xunyuc.xproject.web.bean.proxy.UserProxy;
import com.xunyuc.xproject.web.bean.proxy.UserProxy2;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
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

    public UserProxy2 findUserProxyByName2(EnumSet<UserProxy2.Field> fields, String name) {
        if(fields == null || fields.size() == 0){
            return null;
        }
        int index = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for (UserProxy2.Field field :fields) {
            if (index != 0) {
                sql.append(",");
            }
            sql.append(field.getFiled());
            index++;
        }
        sql.append(" from user where name = (:name) ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        User user = this.namedQueryForObject(sql.toString(), User.class, paramMap);
        return new UserProxy2(fields, user);
    }

}
