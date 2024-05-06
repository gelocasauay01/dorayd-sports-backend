package com.dorayd.sports.features.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.dorayd.sports.core.test_templates.IntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class AuthenticationControllerTest extends IntegrationTest {

    private String testInvalidLoginInput;
    private String testLoginInput;
    private String testRegisterInput;
    private String testInvalidRegisterInput;

    @BeforeAll
    public void setupAll() {
        testInvalidLoginInput = """
            {
                "email": "abc1234560984560984@yahoo.com",
                "password": "password1"
            }
        """;
        testLoginInput = """
            {
                "email": "abc123@yahoo.com",
                "password": "password1"
            }
        """;
        testRegisterInput = """
            {
                "email": "abc12345678@yahoo.com",
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
        testInvalidRegisterInput = """
            {
                "email": "abc123@yahoo.com",
                "password": "password1",
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
    public void givenRegister_whenEmailDoesNotExist_thenReturnAuthenticationResponse() throws Exception {
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
    public void givenRegister_whenEmailExists_thenReturnConflictStatusCode() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInvalidRegisterInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnAuthenticationResponse() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testLoginInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnUnauthorizedStatusCode() throws Exception {
        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInvalidLoginInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }
}
