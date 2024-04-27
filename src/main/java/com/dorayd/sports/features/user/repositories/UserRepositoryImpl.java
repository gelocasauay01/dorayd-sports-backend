package com.dorayd.sports.features.user.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.user.mappers.UserMapper;
import com.dorayd.sports.features.user.models.User;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";
    private final String UPDATE_BY_ID_QUERY = "UPDATE users SET first_name = ?, middle_name = ?, last_name = ?, birth_date = ?, gender = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("users")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                new UserMapper(),
                id
            );
            return Optional.of(user);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User newUser) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", newUser.getFirstName());
        parameters.put("middle_name", newUser.getMiddleName());
        parameters.put("last_name", newUser.getLastName());
        parameters.put("birth_date", newUser.getBirthDate().toString());
        parameters.put("gender", newUser.getGender().toString());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        newUser.setId(newId.longValue());
        return newUser;
    }

    @Override
    public User update(Long id, User updatedUser) {
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(), updatedUser.getBirthDate().toString(), updatedUser.getGender().toString(), id);
        updatedUser.setId(id);
        return updatedUser;
    }

    @Override
    public boolean delete(Long id) {
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }
    
}
