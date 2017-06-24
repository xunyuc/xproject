package com.xunyuc.xproject.web.bean.proxy;

import com.xunyuc.xproject.web.bean.po.User;
import org.apache.commons.lang.ArrayUtils;

/**
 * Created by Xunyuc on 2017/6/24.
 */
public class UserProxy {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SECRET_KEY = "secret_key";
    public static final String LAST_LOGIN_TIME = "last_login_time";

    private String[] needSelectColumns;
    private User user;

    public UserProxy(String[] needSelectColumns, User user) {
        this.needSelectColumns = needSelectColumns;
        this.user = user;
    }

    public String getId() {
        if (needSelectColumns == null || !ArrayUtils.contains(needSelectColumns, ID)) {
            throw new RuntimeException("程序错误，未指定查询出user表id列，该列不能被访问!");
        }
        return user.getId();
    }

    public String getName() {
        if (needSelectColumns == null || !ArrayUtils.contains(needSelectColumns, NAME)) {
            throw new RuntimeException("程序错误，未指定查询出user表name列，该列不能被访问!");
        }
        return user.getName();
    }

    public String getSecretKey() {
        if (needSelectColumns == null || !ArrayUtils.contains(needSelectColumns, SECRET_KEY)) {
            throw new RuntimeException("程序错误，未指定查询出user表secret_key列，该列不能被访问!");
        }
        return user.getSecretKey();
    }

    public Long getLastLoginTime() {
        if (needSelectColumns == null || !ArrayUtils.contains(needSelectColumns, LAST_LOGIN_TIME)) {
            throw new RuntimeException("程序错误，未指定查询出user表LAST_LOGIN_TIME列，该列不能被访问!");
        }
        return user.getLastLoginTime();
    }

}
