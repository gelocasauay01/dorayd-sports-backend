package com.dorayd.sports.features.team.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Slf4j
@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Team> findById(Long id) {
        log.info("Finding Team with id: {}", id);
        return teamRepository.findById(id);
    }

    @Override
    public Team create(Team newTeam) {
        log.info("Saving team to the database: {}", newTeam);

        if(newTeam.getPlayers() != null && !newTeam.getPlayers().isEmpty()) {
            savePlayers(newTeam.getPlayers());
        }

        return teamRepository.create(newTeam);
    }

    @Override
    public Team update(Long id, Team updatedTeam) {
        log.info("Updating team with id {} with {}", id, updatedTeam);
        return teamRepository.update(id, updatedTeam);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting team with id {}", id);
        return teamRepository.delete(id);
    }

    @Override
    public Team addPlayer(User user, Long teamId) {
        log.info("Adding user: {} in team with id {}", user, teamId);

        // Save user when it still does not exist
        if(user.getId() == null) {
            User createdUser = userRepository.create(user);
            user.setId(createdUser.getId());
        }

        return teamRepository.addPlayer(user, teamId);
    }

    private void savePlayers(List<User> players) {
        for(int index = 0; index < players.size(); index++) {
            User user = players.get(index);
            if(user.getId() == null) {
                User createdUser = userRepository.create(user);
                user.setId(createdUser.getId());
            } else {
                Optional<User> queriedUser = userRepository.findById(user.getId());
                players.set(index, queriedUser.orElseThrow());
            }        
        }
    }
}
