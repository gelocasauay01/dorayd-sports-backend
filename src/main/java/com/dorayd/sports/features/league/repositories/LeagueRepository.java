package com.dorayd.sports.features.league.repositories;

import java.util.Optional;

import com.dorayd.sports.features.league.models.League;

/**
 * This interface represents a repository for managing leagues.
 */
public interface LeagueRepository {

    /**
     * Finds a league by its ID.
     *
     * @param id The ID of the league to find.
     * @return An Optional containing the found League, or an empty Optional if no League was found with the given ID.
     */
    Optional<League> findById(Long id);

    /**
     * Creates a new league.
     *
     * @param newLeague The league to create.
     * @return The created League.
     */
    League create(League newLeague);

    /**
     * Updates an existing league.
     *
     * @param id The ID of the league to update.
     * @param updatedLeague The league entity with updated information.
     * @return The updated League.
     */
    League update(Long id, League updatedLeague);

    /**
     * Deletes a league by its ID.
     *
     * @param id The ID of the league to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);

    /**
     * Adds a team to a league.
     *
     * @param teamId The ID of the team to add.
     * @param leagueId The ID of the league where the team will be added.
     * @return The updated League.
     */
    League addTeam(Long teamId, Long leagueId);
}
