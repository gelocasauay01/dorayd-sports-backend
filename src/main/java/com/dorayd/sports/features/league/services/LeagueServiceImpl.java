package com.dorayd.sports.features.league.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

@Service
public class LeagueServiceImpl implements LeagueService {

    private static final Logger LOG = LogManager.getLogger(LeagueServiceImpl.class);
    private final LeagueRepository repository;

    public LeagueServiceImpl(LeagueRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<League> findById(Long id) {
        LOG.info("Finding League with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public League create(League newLeague) {
        LOG.info("Saving league to the database: {}", newLeague);
        return repository.create(newLeague);
    }

    @Override
    public League update(Long id, League updatedLeague) {
        LOG.info("Updating league with id {} with {}", id, updatedLeague);
        return repository.update(id, updatedLeague);
    }

    @Override
    public boolean delete(Long id) {
        LOG.info("Deleting league with id {}", id);
        return repository.delete(id);
    }
    
}
