package com.dorayd.sports.features.team.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.team.dto.TeamDto;
import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;

@Slf4j
@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;

    @Override
    public Optional<Team> findById(long id) {
        log.info("Finding Team with id: {}", id);
        return teamRepository.findById(id);
    }

    @Override
    public Team create(TeamDto newTeam) {
        log.info("Saving team to the database: {}", newTeam);
        return teamRepository.create(newTeam);
    }

    @Override
    public Team update(long id, TeamDto updatedTeam) {
        log.info("Updating team with id {} with {}", id, updatedTeam);
        return teamRepository.update(id, updatedTeam);
    }

    @Override
    public boolean delete(long id) {
        log.info("Deleting team with id {}", id);
        return teamRepository.delete(id);
    }

    @Override
    public Team addPlayer(long userId, long teamId) {
        log.info("Adding user with id: {} in team with id {}", userId, teamId);
        return teamRepository.addPlayer(userId, teamId);
    }

    @Override
    public Team addPlayers(List<Long> userIds, long teamId) {
        log.info("Adding users with the following ids: {} in team with id {}", userIds, teamId);
        return teamRepository.addPlayers(userIds, teamId);
    }
}
