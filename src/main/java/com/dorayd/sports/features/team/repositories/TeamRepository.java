package com.dorayd.sports.features.team.repositories;

import java.util.Optional;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.user.models.User;

/**
 * This interface represents a repository for managing Teams.
 */
public interface TeamRepository {
    /**
     * Finds a Team by its ID.
     *
     * @param id The ID of the Team to find.
     * @return An Optional containing the found Team, or an empty Optional if no Team was found with the given ID.
     */
    Optional<Team> findById(Long id);

    /**
     * Creates a new Team.
     *
     * @param newTeam The Team to create.
     * @return The created Team.
     */
    Team create(Team newTeam);

    /**
     * Updates an existing Team.
     *
     * @param id The ID of the Team to update.
     * @param updatedTeam The Team entity with updated information.
     * @return The updated Team.
     */
    Team update(Long id, Team updatedTeam);

    /**
     * Deletes a Team by its ID.
     *
     * @param id The ID of the Team to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);

    /**
     * Adds a player to a team.
     *
     * @param userId The ID of the user to be added to the team.
     * @param teamId The ID of the team.
     * @return The updated team.
     */
    Team addPlayer(Long userId, Long teamId);
}