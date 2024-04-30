package com.dorayd.sports.features.team;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.services.TeamService;
import com.dorayd.sports.features.user.models.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping(TeamController.TEAM_API_URL)
public class TeamController {
    public static final String TEAM_API_URL = "/api/team";

    private final TeamService service;

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable Long id) {
        final Optional<Team> team = service.findById(id);

        return team.map(value -> ResponseEntity
                .ok()
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());

    }

    @PostMapping
    public ResponseEntity<Team> create(@RequestBody Team newTeam) {
        final Team createdTeam = service.create(newTeam);
        try {
            return ResponseEntity
                .created(new URI(String.format("%s/%d", TEAM_API_URL, createdTeam.getId())))
                .body(createdTeam);
        } catch(URISyntaxException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody Team updatedTeam) {
        return ResponseEntity
            .ok()
            .body(service.update(id, updatedTeam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(service.delete(id)) {
            return ResponseEntity
                .ok()
                .build();
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
    }

    @PostMapping("/{teamId}/add_player")
    public ResponseEntity<Team> addPlayer(@PathVariable Long teamId, @RequestBody User newPlayer) {
        return ResponseEntity
            .ok()
            .body(service.addPlayer(newPlayer, teamId));
    }
    
}
