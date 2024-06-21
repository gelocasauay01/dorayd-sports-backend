package com.dorayd.sports.features.team;

import java.util.List;
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

    private final int ADD_PLAYER_TEAM_ID = 5;

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
        // Arrange
        String requestBody = "{\"name\":\"Greenpark Summer Team\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("Greenpark Summer Team"));
    }

    @Test
    public void givenCreate_whenTeamNameIsNull_thenReturnBadRequestStatus() throws Exception {
        // Arrange
        String requestBody = "{\"name\": null}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Team name cannot be null"));
    }

    @Test
    public void givenCreate_whenTeamNameIsBlank_thenReturnBadRequestStatus() throws Exception {
        // Arrange
        String requestBody = "{\"name\": \"\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Team name must have 3-100 characters"));
    }

    @Test
    public void givenUpdate_whenTeamAndIdExists_thenUpdateAndReturnUpdatedTeam() throws Exception {
        // Arrange
        final int UPDATE_ID = 2;
        String expectedJson = String.format("{\"id\":%d,\"name\":\"Karangalan Team\",\"players\":[]}", UPDATE_ID);
        String requestBody = "{\"name\":\"Karangalan Team\",\"userIds\":[]}";

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenUpdate_whenTeamNameIsNull_thenReturnBadRequestStatus() throws Exception {
        // Arrange
        final int UPDATE_ID = 2;
        String requestBody = "{\"name\":null,\"userIds\":[]}";

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn();

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Team name cannot be null"));
    }

    @Test
    public void givenUpdate_whenTeamNameIsBlank_thenReturnBadRequestStatus() throws Exception {
        // Arrange
        final int UPDATE_ID = 2;
        String requestBody = "{\"name\":\"\",\"userIds\":[]}";

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn();

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Team name must have 3-100 characters"));
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
        final int USER_ID = 1;
        final String EXPECTED_TEAM_NAME = "EG";
        final String EXPECTED_MEMBER_NAME = "Joseph";

        // Act
        MvcResult result = mockMvc.perform(post("/api/team/{id}/add_player?userId={userId}", ADD_PLAYER_TEAM_ID, USER_ID)
            .with(user(userDetails))).andReturn();
        String content = result.getResponse().getContentAsString();
        
        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains(EXPECTED_TEAM_NAME));
        assertTrue(content.contains(EXPECTED_MEMBER_NAME));
    }

    @Test
    public void givenAddPlayers_whenPlayerIdsAreValid_thenAddPlayersToTheTeam() throws Exception {
        //Arrange
        final String EXPECTED_TEAM_NAME = "EG";
        final List<String> EXPECTED_MEMBER_NAMES = List.of("Joseph", "Jiro", "Hayley");
        String requestBody = "[2, 3, 4]";
        

        // Act
        MvcResult result = mockMvc.perform(post("/api/team/{id}/bulk_add_player", ADD_PLAYER_TEAM_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)).andReturn();
        String content = result.getResponse().getContentAsString();
        
        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains(EXPECTED_TEAM_NAME));
        EXPECTED_MEMBER_NAMES.forEach(name -> assertTrue(content.contains(name)));
    }
}
