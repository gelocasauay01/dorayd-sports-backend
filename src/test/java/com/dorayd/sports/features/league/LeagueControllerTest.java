package com.dorayd.sports.features.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.core.test_templates.IntegrationTestWithAuthentication;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class LeagueControllerTest extends IntegrationTestWithAuthentication{

    private static final int UPDATE_ID = 2;
    private static final int VALID_LEAGUE_ID = 4;

    @Test
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() throws Exception {
        // Arrange
        String expectedJson = "{\"id\":1,\"title\":\"Greenpark league\",\"teams\":[]}";
        final int FIND_ID = 1;

        // Act
        MvcResult result = mockMvc.perform(get("/api/league/{id}", FIND_ID).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenFindById_whenLeagueDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/league/{id}", 100).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/league")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"title\":\"Greenpark Summer League\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("Greenpark Summer League"));
    }

    @Test
    public void givenUpdate_whenLeagueAndIdExists_thenUpdateAndReturnUpdatedLeague() throws Exception {
        // Arrange
        String expectedJson = String.format("{\"id\":%d,\"title\":\"Karangalan League\",\"teams\":[]}", UPDATE_ID);

        // Act
        MvcResult result = mockMvc.perform(put("/api/league/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": null,\"title\":\"Karangalan League\"}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenDelete_whenLeagueWithIdExists_thenDeleteLeague() throws Exception {
        // Arrange
        final int DELETE_ID = 3;

        // Act
        MvcResult result = mockMvc.perform(delete("/api/league/{id}", DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenDelete_whenLeagueWithIdDoesNotExists_thenThrowNotFound() throws Exception {
        // Arrange
        final int INVALID_DELETE_ID = 10000;

        // Act
        MvcResult result = mockMvc.perform(delete("/api/league/{id}", INVALID_DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenAddTeam_whenLeagueAndTeamExists_thenReturnLeagueAndStatusOk() throws Exception {
        //Arrange
        final int TEAM_ID = 4;

        // Act
        MvcResult result = mockMvc.perform(post("/api/league/{id}/add_team?teamId={teamId}", VALID_LEAGUE_ID, TEAM_ID)
                .with(user(userDetails))).andReturn();

        // Assert
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("Team Magma"));
    }

    @Test
    public void givenAddTeam_whenLeagueDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        //Arrange
        final int INVALID_LEAGUE_ID = 123123;
        final int VALID_TEAM_ID = 4;

        // Act
        MvcResult result = mockMvc.perform(post("/api/league/{id}/add_team?teamId={teamId}", INVALID_LEAGUE_ID, VALID_TEAM_ID)
                .with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}
