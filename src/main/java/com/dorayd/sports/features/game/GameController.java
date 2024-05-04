package com.dorayd.sports.features.game;

import com.dorayd.sports.features.game.dto.GameDto;
import com.dorayd.sports.features.game.models.Game;
import com.dorayd.sports.features.game.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(GameController.GAME_API_URL)
public class GameController {
     public static final String GAME_API_URL = "/api/game";

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
         Optional<Game> game = service.findById(id);

         return game.map(value -> ResponseEntity.ok().body(value))
             .orElseGet(() -> ResponseEntity.notFound().build());
     }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody GameDto gameDto) {
        Game game = service.create(gameDto);
        try {
            return ResponseEntity
                .created(new URI(String.format("%s/%d", GAME_API_URL, game.getId())))
                .body(game);
        } catch(URISyntaxException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        }
        
    }

    @PatchMapping("/{id}/update_schedule")
    public ResponseEntity<Game> updateSchedule(@PathVariable Long id, @RequestParam LocalDateTime schedule) {
        Game game = service.updateSchedule(id, schedule);
        return ResponseEntity
            .ok()
            .body(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }
}
