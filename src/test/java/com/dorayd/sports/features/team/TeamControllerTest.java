package com.dorayd.sports.features.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

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

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.services.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TeamControllerTest {
    
    @MockBean
    private TeamService service;

    @Autowired
    private MockMvc mockMvc;

    private Team dummyTeam;
    private String dummyTeamJson;

    @BeforeAll
    private void setupAll() {
        dummyTeam = new Team(1l, "Greenpark Team");
        dummyTeamJson = "{\"id\":1,\"name\":\"Greenpark Team\"}";
    }
    
    @WithMockUser("gelo")
    @Test
    @DisplayName("GET /team/1 - Found")
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() throws Exception {
        // Arrange
        doReturn(Optional.of(dummyTeam)).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyTeamJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("GET /team/1 - Not Found")
    public void givenFindById_whenTeamDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Arrange
        doReturn(Optional.empty()).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/team/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("POST /team - CREATED")
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() throws Exception {
        // Arrange
        doReturn(dummyTeam).when(service).create(any());

        // Act
        MvcResult result = mockMvc.perform(post("/api/team")
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"title\":\"Greenpark Team\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyTeamJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("PUT /Team/{id} - OK")
    public void givenUpdate_whenTeamAndIdExists_thenUpdateAndReturnUpdatedTeam() throws Exception {
        // Arrange
        Team updatedTeam = new Team(1l, "Karangalan Team");
        String expectedJson = "{\"id\":1,\"name\":\"Karangalan Team\"}";
        doReturn(updatedTeam).when(service).update(anyLong(), any());

        //Act
        MvcResult result = mockMvc.perform(put("/api/team/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": null,\"name\":\"Karangalan Team\"}")).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @WithMockUser("gelo")
    @Test
    @DisplayName("DELETE /Team/{id} - OK")
    public void givenDelete_whenTeamWithIdExists_thenDeleteTeam() throws Exception {
        // Arrange 
        doReturn(true).when(service).delete(anyLong());

        // Act
        MvcResult result = mockMvc.perform(delete("/api/team/{id}", 1)).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
