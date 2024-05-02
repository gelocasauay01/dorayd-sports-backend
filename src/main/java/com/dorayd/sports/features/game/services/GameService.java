package com.dorayd.sports.features.game.services;

import com.dorayd.sports.features.game.dto.GameDto;
import com.dorayd.sports.features.game.models.Game;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for managing games.
 */
public interface GameService {

    /**
     * Finds a game by its ID.
     *
     * @param id The ID of the game.
     * @return An Optional containing the game if found, or an empty Optional if not.
     */
    Optional<Game> findById(Long id);

    /**
     * Creates a new game.
     *
     * @param gameDto The data transfer object containing the details of the game to be created.
     * @return The created game.
     */
    Game create(GameDto gameDto);

    /**
     * Updates the schedule of a game.
     *
     * @param id The ID of the game to be updated.
     * @param schedule The new schedule for the game.
     * @return The updated game.
     */
    Game updateSchedule(Long id, LocalDateTime schedule);

    /**
     * Deletes a game by its ID.
     *
     * @param id The ID of the game to be deleted.
     * @return true if the game was successfully deleted, false otherwise.
     */
    boolean delete(Long id);
}

