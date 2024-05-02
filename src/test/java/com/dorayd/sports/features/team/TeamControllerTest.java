package com.dorayd.sports.features.team;

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

public class TeamControllerTest extends IntegrationTestWithAuthentication{

    @Test
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() throws Exception {
        // Arrange
        final int FIND_ID = 1;
        String expectedJson = "{\"id\":1,\"name\":\"Team Rocket\",\"players\":[]}";

        // Act
        MvcResult result = mockMvc.perform(get("/api/team/{id}", FIND_ID).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenFindById_whenTeamDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", 100).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"name\":\"Greenpark Summer Team\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("Greenpark Summer Team"));
    }

    @Test
    public void givenUpdate_whenTeamAndIdExists_thenUpdateAndReturnUpdatedTeam() throws Exception {
        // Arrange
        final int UPDATE_ID = 2;
        String expectedJson = String.format("{\"id\":%d,\"name\":\"Karangalan Team\",\"players\":[]}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Karangalan Team\",\"players\":[]}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenDelete_whenTeamWithIdExists_thenDeleteTeam() throws Exception {
        // Arrange
        final int DELETE_ID = 3;

        // Act
        MvcResult result = mockMvc.perform(delete("/api/team/{id}", DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenAddPlayer_whenPlayerIsValid_thenAddPlayerToTheTeam() throws Exception {
        //Arrange
        final long PLAYER_ID = 5L;
        String requestBody = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/team/{id}/add_player", PLAYER_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)).andReturn();
        
        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("EG"));
    }
}
