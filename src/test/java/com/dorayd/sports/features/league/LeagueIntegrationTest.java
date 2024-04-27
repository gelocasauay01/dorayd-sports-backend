package com.dorayd.sports.features.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.core.test_templates.IntegrationTestWithAuthentication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class LeagueIntegrationTest extends IntegrationTestWithAuthentication{

    // Refer to data-test.sql to know the values of each IDs
    private final int FIND_ID = 1;
    private final int UPDATE_ID = 2;
    private final int DELETE_ID = 3;
    
    @Test
    @DisplayName("GET /league/1 - Found")
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() throws Exception {
        // Arrange
        String expectedJson = "{\"id\":1,\"title\":\"Greenpark league\"}";

        // Act 
        MvcResult result = mockMvc.perform(get("/api/league/{id}", FIND_ID).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /league/1 - Not Found")
    public void givenFindById_whenLeagueDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/league/{id}", 100).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test   
    @DisplayName("POST /league - CREATED")
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/league")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"title\":\"Greenpark Summer League\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertTrue(result.getResponse().getContentAsString().contains("Greenpark Summer League"));
    }

    @Test
    @DisplayName("PUT /league/{id} - OK")
    public void givenUpdate_whenLeagueAndIdExists_thenUpdateAndReturnUpdatedLeague() throws Exception {
        // Arrange
        String expectedJson = String.format("{\"id\":%d,\"title\":\"Karangalan League\"}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/league/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": null,\"title\":\"Karangalan League\"}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("DELETE /league/{id} - OK")
    public void givenDelete_whenLeagueWithIdExists_thenDeleteLeague() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(delete("/api/league/{id}", DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
