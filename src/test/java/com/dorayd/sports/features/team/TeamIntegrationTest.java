package com.dorayd.sports.features.team;

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
public class TeamIntegrationTest {

    private final int FIND_ID = 1;
    private final int UPDATE_ID = 2;
    private final int DELETE_ID = 3;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /team/1 - Found")
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() throws Exception {
        // Arrange
        String expectedJson = "{\"id\":1,\"name\":\"Team Rocket\"}";

        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", FIND_ID)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /team/1 - Not Found")
    public void givenFindById_whenTeamDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", 100)).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /team - CREATED")
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
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
        String expectedJson = String.format("{\"id\":%d,\"name\":\"Karangalan Team\"}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", UPDATE_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": null,\"name\":\"Karangalan Team\"}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("DELETE /team/{id} - OK")
    public void givenDelete_whenTeamWithIdExists_thenDeleteTeam() throws Exception {

        // Act
        MvcResult result = mockMvc.perform(delete("/api/team/{id}", DELETE_ID)).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
