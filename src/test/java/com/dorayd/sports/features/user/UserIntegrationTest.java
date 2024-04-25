package com.dorayd.sports.features.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserIntegrationTest {

    // Refer to data-test.sql to know the values of each IDs
    private final int FIND_ID = 1;
    private final int UPDATE_ID = 2;
    private final int DELETE_ID = 3;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /user/1 - Found")
    public void givenFindById_whenUserExists_thenReturnSpecificUser() throws Exception {
        // Arrange
        String expectedJson = "{\"id\":1,\"firstName\":\"Joseph\",\"middleName\":\"Mardo\",\"lastName\":\"Casauay\",\"birthDate\":\"1999-08-01\",\"gender\":\"MALE\"}";

        // Act 
        MvcResult result = mockMvc.perform(get("/api/user/{id}", FIND_ID)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /user/1 - Not Found")
    public void givenFindById_whenUserDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/user/{id}", 100)).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /user - CREATED")
    public void givenCreate_whenUserIsValid_thenReturnCreatedUser() throws Exception {
        // Act
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}";
        MvcResult result = mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(expected)).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertTrue(result.getResponse().getContentAsString().contains("Boiser"));
    }

    @Test
    @DisplayName("PUT /user/{id} - OK")
    public void givenUpdate_whenUserAndIdExists_thenUpdateAndReturnUpdatedUser() throws Exception {
        // Arrange
        String expected = String.format("{\"id\":%d,\"firstName\":\"Reynald\",\"middleName\":null,\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/user/{id}", UPDATE_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expected)).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("DELETE /user/{id} - OK")
    public void givenDelete_whenUserWithIdExists_thenDeleteUser() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(delete("/api/user/{id}", DELETE_ID)).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
