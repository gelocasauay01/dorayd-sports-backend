package com.dorayd.sports.features.team.services;

import java.util.Optional;
import java.util.List;

import com.dorayd.sports.features.team.dto.TeamDto;
import com.dorayd.sports.features.team.models.Team;

/**
 * This interface represents the service for managing teams.
 */
public interface TeamService {
    /**
     * Finds a team by its ID.
     *
     * @param id The ID of the team to find.
     * @return An Optional containing the found Team, or an empty Optional if no Team was found with the given ID.
     */
    Optional<Team> findById(long id);

    /**
     * Creates a new team.
     *
     * @param newTeam The new Team to create.
     * @return The created Team.
     */
    Team create(TeamDto newTeam); 

    /**
     * Updates an existing team.
     *
     * @param id The ID of the team to update.
     * @param updatedTeam The Team entity with updated information.
     * @return The updated Team.
     */
    Team update(long id, TeamDto updatedTeam);

    /**
     * Deletes a team by its ID.
     *
     * @param id The ID of the team to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(long id);

    /**
     * Adds a player to a team.
     *
     * @param userId The ID of user to be added to the team.
     * @param teamId The ID of the team.
     * @return The updated team.
     */
    Team addPlayer(long userId, long teamId);

     /**
     * Adds multiple players to a team.
     *
     * @param userIds The list of user IDs to be added to the team.
     * @param teamId The ID of the team.
     * @return The updated team.
     */
    Team addPlayers(List<Long> userIds, long teamId);
}
