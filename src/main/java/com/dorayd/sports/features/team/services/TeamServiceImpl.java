package com.dorayd.sports.features.team.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Service
public class TeamServiceImpl implements TeamService{
    
    @Autowired
    private TeamRepository repository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(TeamServiceImpl.class);


    @Override
    public Optional<Team> findById(Long id) {
        logger.info("Finding Team with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public Team create(Team newTeam) {
        logger.info("Saving team to the database: {}", newTeam);

        if(newTeam.getPlayers() != null && !newTeam.getPlayers().isEmpty()) {
            savePlayers(newTeam.getPlayers());
        }

        return repository.create(newTeam);
    }

    @Override
    public Team update(Long id, Team updatedTeam) {
        logger.info("Updating team with id {} with {}", id, updatedTeam);
        return repository.update(id, updatedTeam);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting team with id {}", id);
        return repository.delete(id);
    }

    @Override
    public Team addPlayer(User user, Long teamId) {
        logger.info("Adding user: {} in team with id {}", user, teamId);

        // Save user when it still does not exist
        if(user.getId() == null) {
            User createdUser = userRepository.create(user);
            user.setId(createdUser.getId());
        }

        return repository.addPlayer(user, teamId);
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
