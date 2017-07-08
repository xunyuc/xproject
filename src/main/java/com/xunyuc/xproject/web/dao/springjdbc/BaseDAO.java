package com.xunyuc.xproject.web.dao.springjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

/**
 * Created by Xunyuc on 2017/6/24.
 */
public abstract class BaseDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected <T> T namedQueryForObject (String sql, Class<T> classType,Map<String, Object> paramMap) {
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(classType);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        try {
            return namedParameterJdbcTemplate.queryForObject (sql, parameterSource,rowMapper);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
