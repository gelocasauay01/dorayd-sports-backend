package com.dorayd.sports.features.league.services;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.league.dto.LeagueDto;
import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

@Slf4j
@AllArgsConstructor
@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;

    @Override
    public Optional<League> findById(long id) {
        log.info("Finding League with ID: {}", id);
        return leagueRepository.findById(id);
    }

    @Override
    public League create(LeagueDto newLeague) {
        log.info("Saving league to the database: {}", newLeague);
        return leagueRepository.create(newLeague);
    }

    @Override
    public League update(long id, LeagueDto updatedLeague) {
        log.info("Updating league with ID {} with {}", id, updatedLeague);
        return leagueRepository.update(id, updatedLeague);
    }

    @Override
    public boolean delete(long id) {
        log.info("Deleting league with id {}", id);
        return leagueRepository.delete(id);
    }

    @Override
    public League addTeam(long teamId, long leagueId) {
        log.info("Adding team with ID: {} to league with ID {}", teamId, leagueId);
        return leagueRepository.addTeam(teamId, leagueId);
    }

}
