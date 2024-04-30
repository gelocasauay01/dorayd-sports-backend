package com.dorayd.sports.features.league.services;

import java.util.Optional;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

@Slf4j
@AllArgsConstructor
@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    @Override
    public Optional<League> findById(Long id) {
        log.info("Finding League with id: {}", id);
        return leagueRepository.findById(id);
    }

    @Override
    public League create(League newLeague) {
        log.info("Saving league to the database: {}", newLeague);
        return leagueRepository.create(newLeague);
    }

    @Override
    public League update(Long id, League updatedLeague) {
        log.info("Updating league with id {} with {}", id, updatedLeague);
        return leagueRepository.update(id, updatedLeague);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting league with id {}", id);
        return leagueRepository.delete(id);
    }

    @Override
    public League addTeam(Team team, Long leagueId) {
        log.info("Adding team: {} to league with id {}",team, leagueId);

        // Save team if it still does not exist
        if(team.getId() == null) {
            team = teamRepository.create(team);
        }

        return leagueRepository.addTeam(team.getId(), leagueId);
    }

}
