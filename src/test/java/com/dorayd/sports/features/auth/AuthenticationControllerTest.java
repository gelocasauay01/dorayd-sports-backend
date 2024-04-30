package com.dorayd.sports.features.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.features.auth.models.AuthenticationResponse;
import com.dorayd.sports.features.auth.services.AuthenticationService;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService service;

    @Autowired
    private MockMvc mockMvc;

    private AuthenticationResponse dummyResponse;
    private String dummyJson;
    private String testInput;
  

    @BeforeAll
    public void setupAll() {
        User dummyUser = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        dummyResponse = new AuthenticationResponse(dummyUser, "testtoken");
        dummyJson = "{\"user\":{\"id\":1,\"firstName\":\"Joseph\",\"middleName\":\"Mardo\",\"lastName\":\"Casauay\",\"birthDate\":\"1999-08-01\",\"gender\":\"MALE\"},\"token\":\"testtoken\"}";
        testInput = """
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
    }   
    
    @Test
    @DisplayName("POST /auth/register -  CREATED")
    public void givenRegister_whenUsernameDoesNotExist_thenReturnAuthenticationResponse() throws Exception {
        // Arrange
        doReturn(dummyResponse).when(service).register(any());

        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(dummyJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("POST /auth/register -  CONFLICT")
    public void givenRegister_whenUsernameExists_thenReturnConflictStatusCode() throws Exception {
        // Arrange
        doThrow(DuplicateKeyException.class).when(service).register(any());

        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /auth/login - OK")
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnAuthenticationResponse() throws Exception {
        // Arrange
        doReturn(dummyResponse).when(service).authenticate(any());

        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(dummyJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("POST /auth/login - UNAUTHORIZED")
    public void givenLogin_whenUserExistsAndCorrectCredentials_thenReturnUnauthorizedStatusCode() throws Exception {
        // Arrange
        doThrow(BadCredentialsException.class).when(service).authenticate(any());

        //Act
        MvcResult result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testInput)).andReturn();

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }
}
