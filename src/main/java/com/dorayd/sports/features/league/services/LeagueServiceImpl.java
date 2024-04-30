package com.dorayd.sports.features.league.services;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

@Slf4j
@AllArgsConstructor
@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository repository;

    @Override
    public Optional<League> findById(Long id) {
        log.info("Finding League with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public League create(League newLeague) {
        log.info("Saving league to the database: {}", newLeague);
        return repository.create(newLeague);
    }

    @Override
    public League update(Long id, League updatedLeague) {
        log.info("Updating league with id {} with {}", id, updatedLeague);
        return repository.update(id, updatedLeague);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting league with id {}", id);
        return repository.delete(id);
    }
    
}
