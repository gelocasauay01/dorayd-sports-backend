package com.dorayd.sports.core.test_templates;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


public abstract class IntegrationTestWithAuthentication extends IntegrationTest {

    protected UserDetails userDetails;

    @BeforeAll
    protected void setupAll() {
        User user = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        userDetails = new UserAuth("abc123@yahoo.com", "password1", Role.USER, user);
    }
    
    @BeforeEach
    @Override
    protected void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }
}
