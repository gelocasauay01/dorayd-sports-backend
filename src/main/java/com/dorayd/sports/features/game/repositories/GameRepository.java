package com.dorayd.sports.features.game.repositories;

import com.dorayd.sports.features.game.dto.GameDto;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This interface represents a repository for managing games.
 */
public interface GameRepository {

    /**
     * Finds a game by its ID.
     *
     * @param id The ID of the game to find.
     * @return An Optional that may contain the found game.
     */
    Optional<GameDto> findById(Long id);

    /**
     * Creates a new game based on the provided input.
     *
     * @param input The data transfer object containing the details of the game to be created.
     * @return The created game.
     */
    GameDto create(GameDto input);

    /**
     * Updates the schedule of a game.
     *
     * @param id The ID of the game to update.
     * @param schedule The new schedule for the game.
     * @return The updated game.
     */
    GameDto updateSchedule(Long id, LocalDateTime schedule);

    /**
     * Deletes a game by its ID.
     *
     * @param id The ID of the game to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);
}
