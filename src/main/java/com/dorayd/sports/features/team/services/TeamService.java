package com.dorayd.sports.features.team.services;

import java.util.Optional;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.user.models.User;

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
    Optional<Team> findById(Long id);

    /**
     * Creates a new team.
     *
     * @param newTeam The new Team to create.
     * @return The created Team.
     */
    Team create(Team newTeam); 

    /**
     * Updates an existing team.
     *
     * @param id The ID of the team to update.
     * @param updatedTeam The Team entity with updated information.
     * @return The updated Team.
     */
    Team update(Long id, Team updatedTeam);

    /**
     * Deletes a team by its ID.
     *
     * @param id The ID of the team to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);

    Team addPlayer(User user, Long teamId);
}
