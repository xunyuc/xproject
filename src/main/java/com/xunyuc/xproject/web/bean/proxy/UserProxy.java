package com.xunyuc.xproject.web.bean.proxy;

import com.xunyuc.xproject.web.bean.entity.User;

import java.util.EnumSet;

/**
 * Created by Xunyuc on 2017/6/24.
 */
public class UserProxy {

    public enum Field {
        ID("id"),NAME("name"),SECRET_KEY("secret_key"),LAST_LOGIN_TIME("last_login_time");

        private String filed;

        Field(String filed) {
            this.filed = filed;
        }

        public String getFiled() {
            return filed;
        }
    }

    private EnumSet<Field> needSelectColumns;
    private User user;

    public UserProxy(EnumSet<Field> needSelectColumns, User user) {
        this.needSelectColumns = needSelectColumns;
        this.user = user;
    }

    public String getId() {
        if (needSelectColumns == null || !needSelectColumns.contains(Field.ID)) {
            throw new RuntimeException("程序错误，未指定查询出user表id列，该列不能被访问!");
        }
        return user.getId();
    }

    public String getName() {
        if (needSelectColumns == null || !needSelectColumns.contains(Field.NAME)) {
            throw new RuntimeException("程序错误，未指定查询出user表name列，该列不能被访问!");
        }
        return user.getName();
    }

    public String getSecretKey() {
        if (needSelectColumns == null || !needSelectColumns.contains(Field.SECRET_KEY)) {
            throw new RuntimeException("程序错误，未指定查询出user表secret_key列，该列不能被访问!");
        }
        return user.getSecretKey();
    }

    public Long getLastLoginTime() {
        if (needSelectColumns == null || !needSelectColumns.contains(Field.LAST_LOGIN_TIME)) {
            throw new RuntimeException("程序错误，未指定查询出user表LAST_LOGIN_TIME列，该列不能被访问!");
        }
        return user.getLastLoginTime();
    }

}
