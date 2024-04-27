package com.dorayd.sports.features.user.mappers;

import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

public class UserMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("middle_name"),
            rs.getString("last_name"),
            LocalDate.parse(rs.getString("birth_date")),
            Gender.valueOf(rs.getString("gender"))
        );
    }
    
}
