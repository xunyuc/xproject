package com.xunyuc.xproject.web.dao.springjdbc;

import com.xunyuc.xproject.web.utils.CommonDAOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by chenwh1 on 2017/7/12.
 */
public class CommonDAO<T> {

    private Class<T> entityClass;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings({ "unchecked" })
    public CommonDAO() {
        this.entityClass = null;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    public int save(T entity) {
        String insertSql = CommonDAOUtil.generateInsertSql(entityClass);
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
        return namedParameterJdbcTemplate.update (insertSql, paramSource);
    }
}
