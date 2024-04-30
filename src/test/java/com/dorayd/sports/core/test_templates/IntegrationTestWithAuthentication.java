package com.dorayd.sports.core.test_templates;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public abstract class IntegrationTestWithAuthentication {

    @Autowired
    protected WebApplicationContext applicationContext;
    protected MockMvc mockMvc;
    protected UserDetails userDetails;

    @BeforeAll
    protected void setupAll() {
        User user = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        userDetails = new UserAuth("abc123", "password1", Role.USER, user);
    }
    
    @BeforeEach
    protected void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext).apply(springSecurity())
            .build();
    }
}
