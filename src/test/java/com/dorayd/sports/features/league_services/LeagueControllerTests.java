package com.dorayd.sports.features.league_services;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.services.LeagueService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class LeagueControllerTests {
    
    @MockBean
    private LeagueService service;

    @Autowired
    private MockMvc mockMvc;

    private League dummyLeague;
    private String dummyLeagueJson;

    @BeforeAll
    private void setupAll() {
        dummyLeague = new League(1l, "Greenpark Summer League");
        dummyLeagueJson = "{\"id\":1,\"title\":\"Greenpark Summer League\"}";
    }
    
    @Test
    @DisplayName("GET /league/1 - Found")
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() throws Exception {
        // Arrange
        doReturn(Optional.of(dummyLeague)).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/league/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyLeagueJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /league/1 - Not Found")
    public void givenFindById_whenLeagueDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Arrange
        doReturn(Optional.empty()).when(service).findById(1l);

        // Act 
        MvcResult result = mockMvc.perform(get("/api/league/{id}", 1)).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("POST /league - CREATED")
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() throws Exception {
        // Arrange
        doReturn(dummyLeague).when(service).create(any());

        // Act
        MvcResult result = mockMvc.perform(post("/api/league")
            .contentType(MediaType.APPLICATION_JSON)
            .content( "{\"id\": null,\"title\":\"Greenpark Summer League\"}")).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(result.getResponse().getContentType()));
        assertEquals(dummyLeagueJson, result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("PUT /league/{id} - OK")
    public void givenUpdate_whenLeagueAndIdExists_thenUpdateAndReturnUpdatedLeague() throws Exception {
        // Arrange
        League updatedLeague = new League(1l, "Karangalan League");
        String expectedJson = "{\"id\":1,\"title\":\"Karangalan League\"}";
        doReturn(updatedLeague).when(service).update(anyLong(), any());

        //Act
        MvcResult result = mockMvc.perform(put("/api/league/{id}", 1)
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
        // Arrange 
        doReturn(true).when(service).delete(anyLong());

        // Act
        MvcResult result = mockMvc.perform(delete("/api/league/{id}", 1)).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}
