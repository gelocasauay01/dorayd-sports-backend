package com.dorayd.sports.features.auth.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Repository
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final UserRepository userRepository;

    public UserAuthRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("user_auth");
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        try {
            final String FIND_BY_USERNAME_QUERY = "SELECT * FROM user_auth WHERE email = ?";
            UserAuth queriedUserAuth = jdbcTemplate.queryForObject(FIND_BY_USERNAME_QUERY,
                (rs, rowNum) -> new UserAuth(
                    rs.getString("email"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role")),
                    User.builder().id(rs.getLong("user_id")).build()
                ),
                email
            );
            queriedUserAuth.setUser(userRepository.findById(queriedUserAuth.getUser().getId()).orElseThrow());
            return Optional.of(queriedUserAuth);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserAuth create(UserAuth newUserAuth) {

        if(newUserAuth.getUser().getId() == 0) {
            User registeredUser = userRepository.create(newUserAuth.getUser());
            newUserAuth.setUser(registeredUser);
        }
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", newUserAuth.getEmail());
        parameters.put("password", newUserAuth.getPassword());
        parameters.put("role", newUserAuth.getRole().toString());
        parameters.put("user_id", newUserAuth.getUser().getId());
        simpleJdbcInsert.execute(parameters);
        return newUserAuth;
    }

    @Override
    public boolean updatePassword(String password, String email) {
        final String UPDATE_PASSWORD_BY_USERNAME_QUERY = "UPDATE user_auth SET password = ? WHERE email = ?";
        int updatedCount = jdbcTemplate.update(UPDATE_PASSWORD_BY_USERNAME_QUERY, password, email);
        return updatedCount > 0;
    }
    
}
