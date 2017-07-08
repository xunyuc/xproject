package com.xunyuc.xproject.web.dao.springjdbc;

import com.xunyuc.xproject.web.dao.IUserInfoDAO;
import com.xunyuc.xproject.web.entity.UserInfo;
import com.xunyuc.xproject.web.proxy.UserProxy;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@Repository("springUserInfoDAO")
public class UserInfoDAOImpl extends BaseDAO implements IUserInfoDAO {

    private static String findSql = "select * from user_info where name = (:name)";

    public UserInfo findUserByName(String name) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        UserInfo user = this.namedQueryForObject(findSql, UserInfo.class, paramMap);
        return user;
    }

    public UserProxy findUserProxyByName2(EnumSet<UserProxy.Field> fields, String name) {
        if(fields == null || fields.size() == 0){
            return null;
        }
        int index = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for (UserProxy.Field field :fields) {
            if (index != 0) {
                sql.append(",");
            }
            sql.append(field.getFiled());
            index++;
        }
        sql.append(" from user where name = (:name) ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        UserInfo user = this.namedQueryForObject(sql.toString(), UserInfo.class, paramMap);
        return new UserProxy(fields, user);
    }

}
