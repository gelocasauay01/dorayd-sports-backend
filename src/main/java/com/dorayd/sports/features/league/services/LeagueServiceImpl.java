package com.dorayd.sports.features.league.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueRepository repository;
    private static final Logger logger = LogManager.getLogger(LeagueServiceImpl.class);


    @Override
    public Optional<League> findById(Long id) {
        logger.info("Finding League with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public League create(League newLeague) {
        logger.info("Saving league to the database: {}", newLeague);
        return repository.create(newLeague);
    }

    @Override
    public League update(Long id, League updatedLeague) {
        logger.info("Updating league with id {} with {}", id, updatedLeague);
        return repository.update(id, updatedLeague);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting league with id {}", id);
        return repository.delete(id);
    }
    
}
