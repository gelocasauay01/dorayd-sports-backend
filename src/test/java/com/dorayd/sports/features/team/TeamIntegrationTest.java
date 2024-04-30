package com.dorayd.sports.features.team;

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

public class TeamIntegrationTest extends IntegrationTestWithAuthentication{

    private final int UPDATE_ID = 2;

    @Test
    @DisplayName("GET /team/1 - Found")
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() throws Exception {
        // Arrange
        String expectedJson = "{\"id\":1,\"name\":\"Team Rocket\",\"players\":[]}";

        // Act 
        // Refer to data-test.sql to know the values of each IDs
        int FIND_ID = 1;
        MvcResult result = mockMvc.perform(get("/api/team/{id}", FIND_ID).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /team/1 - Not Found")
    public void givenFindById_whenTeamDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", 100).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /team - CREATED")
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"name\":\"Greenpark Summer Team\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertTrue(result.getResponse().getContentAsString().contains("Greenpark Summer Team"));
    }

    @Test
    @DisplayName("PUT /team/{id} - OK")
    public void givenUpdate_whenTeamAndIdExists_thenUpdateAndReturnUpdatedTeam() throws Exception {
        // Arrange
        String expectedJson = String.format("{\"id\":%d,\"name\":\"Karangalan Team\",\"players\":[]}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Karangalan Team\",\"players\":[]}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("DELETE /team/{id} - OK")
    public void givenDelete_whenTeamWithIdExists_thenDeleteTeam() throws Exception {

        // Act
        int DELETE_ID = 3;
        MvcResult result = mockMvc.perform(delete("/api/team/{id}", DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /team/{id}/add_player - OK")
    public void givenAddPlayer_whenPlayerIsValid_thenAddPlayerToTheTeam() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/team/{id}/add_player", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertTrue(result.getResponse().getContentAsString().contains("OG"));
    }
}
