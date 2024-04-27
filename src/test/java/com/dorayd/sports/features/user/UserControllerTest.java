package com.dorayd.sports.features.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Optional;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {
    
    @MockBean
    private UserService service;

    @Autowired
    private MockMvc mockMvc;

    private User dummyUser;
    private String dummyUserJson;

    @BeforeAll
    private void setupAll() {
        dummyUser = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        dummyUserJson = "{\"id\":1,\"firstName\":\"Joseph\",\"middleName\":\"Mardo\",\"lastName\":\"Casauay\",\"birthDate\":\"1999-08-01\",\"gender\":\"MALE\"}";
    }
    
    @WithMockUser("gelo")
    @Test
    @DisplayName("GET /user/1 - Found")
    public void givenFindById_whenUserExists_thenReturnSpecificUser() throws Exception {
        // Arrange
        doReturn(Optional.of(dummyUser)).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/user/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyUserJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("GET /user/1 - Not Found")
    public void givenFindById_whenUserDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Arrange
        doReturn(Optional.empty()).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/user/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("POST /user - CREATED")
    public void givenCreate_whenUserIsValid_thenReturnCreatedUser() throws Exception {
        // Arrange
        doReturn(dummyUser).when(service).create(any());

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"title\":\"Greenpark User\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyUserJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("PUT /User/{id} - OK")
    public void givenUpdate_whenUserAndIdExists_thenUpdateAndReturnUpdatedUser() throws Exception {
        // Arrange
        User updatedUser = new User(1l, "Reynald", null, "Boiser", LocalDate.of(1999, 8, 1), Gender.NON_BINARY);
        String expectedJson = "{\"id\":1,\"firstName\":\"Reynald\",\"middleName\":null,\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}";
        doReturn(updatedUser).when(service).update(anyLong(), any());

        //Act
        MvcResult result = mockMvc.perform(put("/api/user/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": null,\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("DELETE /User/{id} - OK")
    public void givenDelete_whenUserWithIdExists_thenDeleteUser() throws Exception {
        // Arrange 
        doReturn(true).when(service).delete(anyLong());

        // Act
        MvcResult result = mockMvc.perform(delete("/api/user/{id}", 1)).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
