package com.dorayd.sports.features.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String testInvalidLoginInput;
    private String testLoginInput;
    private String testRegisterInput;
  

    @BeforeAll
    public void setupAll() {
        testInvalidLoginInput = """
            {
                "username": "abc1234560984560984",
                "password": "password1",
                "role": "USER",
                "user": {
                    "firstName": "Joseph",
                    "middleName": "Mardo",
                    "lastName": "Casauay",
                    "birthDate": "1999-08-01",
                    "gender": "MALE"
                }
            }
        """;
        testLoginInput = """
            {
                "username": "abc123",
                "password": "password1",
                "role": "USER",
                "user": {
                    "firstName": "Joseph",
                    "middleName": "Mardo",
                    "lastName": "Casauay",
                    "birthDate": "1999-08-01",
                    "gender": "MALE"
                }
            }
        """;
        testRegisterInput = """
            {
                "username": "abc12345678",
                "password": "password12345",
                "role": "USER",
                "user": {
                    "firstName": "Jake",
                    "middleName": "Mardo",
                    "lastName": "Carrera",
                    "birthDate": "1999-08-01",
                    "gender": "MALE"
                }
            }
        """;
    }   
    
    @Test
    @DisplayName("POST /auth/register -  CREATED")
    public void givenRegister_whenUsernameDoesNotExist_thenReturnAuthenticationResponse() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testRegisterInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("user"));
        assertTrue(result.getResponse().getContentAsString().contains("token"));
    }

    @Test
    @DisplayName("POST /auth/register -  CONFLICT")
    public void givenRegister_whenUsernameExists_thenReturnConflictStatusCode() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testLoginInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /auth/login - OK")
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnAuthenticationResponse() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testLoginInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /auth/login - UNAUTHORIZED")
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnUnauthorizedStatusCode() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInvalidLoginInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }
}
