package com.dorayd.sports.features.league;

import com.dorayd.sports.features.team.models.Team;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.services.LeagueService;

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
@RequestMapping(LeagueController.LEAGUE_API_URL)
public class LeagueController {
    public static final String LEAGUE_API_URL = "/api/league";

    private final LeagueService service;

    @GetMapping("/{id}")
    public ResponseEntity<League> findById(@PathVariable Long id) {
        final Optional<League> league = service.findById(id);

        return league.map(value -> ResponseEntity
                .ok()
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());

    }

    @PostMapping
    public ResponseEntity<League> create(@RequestBody League newLeague) {
        final League createdLeague = service.create(newLeague);
        try {
            return ResponseEntity
                .created(new URI(String.format("%s/%d", LEAGUE_API_URL, createdLeague.getId())))
                .body(createdLeague);
        } catch(URISyntaxException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<League> update(@PathVariable Long id, @RequestBody League updatedLeague) {
        return ResponseEntity
            .ok()
            .body(service.update(id, updatedLeague));
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

    @PostMapping("/{leagueId}/add_team")
    public ResponseEntity<League> addTeam(@PathVariable Long leagueId, @RequestBody Team newTeam) {
        return ResponseEntity
                .ok()
                .body(service.addTeam(newTeam, leagueId));
    }
    
}
