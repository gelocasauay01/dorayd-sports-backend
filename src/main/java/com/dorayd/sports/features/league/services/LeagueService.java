package com.dorayd.sports.features.league.services;

import java.util.Optional;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.team.models.Team;

/**
 * This interface represents the service for managing leagues.
 */
public interface LeagueService {

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
     * @param newLeague The new League to create.
     * @return The created League.
     */
    League create(League newLeague); 

    /**
     * Updates an existing league.
     *
     * @param id The ID of the league to update.
     * @param updatedLeague The League entity with updated information.
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
     * @param team The team to be added to the league.
     * @param leagueId The ID of the league.
     * @return The updated league.
     */
    League addTeam(Team team, Long leagueId);
}
