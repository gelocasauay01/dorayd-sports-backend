package com.dorayd.sports.helpers;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class SqlQueryExecutor {
    private SqlQueryExecutor() {}

    public static long insert(String insertStatement, JdbcTemplate jdbcTemplate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
                return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
