package com.dorayd.sports.features.team.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    private TeamRepository repository;
    private static final Logger logger = LogManager.getLogger(TeamServiceImpl.class);


    @Override
    public Optional<Team> findById(Long id) {
        logger.info("Finding Team with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public Team create(Team newTeam) {
        logger.info("Saving team to the database: {}", newTeam);
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
}
