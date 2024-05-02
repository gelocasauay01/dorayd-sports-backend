package com.dorayd.sports.features.game.services;

import com.dorayd.sports.features.game.dto.GameDto;
import com.dorayd.sports.features.game.models.Game;
import com.dorayd.sports.features.game.repositories.GameRepository;
import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;
import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public GameServiceImpl(GameRepository gameRepository, LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<Game> findById(Long id) {
        log.info("Finding game with id: {}", id);
        Optional<GameDto> gameDto = gameRepository.findById(id);
        return gameDto.map(this::convertDtoToGame);
    }

    @Override
    public Game create(GameDto gameDto) {
        log.info("Creating game with inputs: {}", gameDto);
        GameDto createdGame = gameRepository.create(gameDto);
        return convertDtoToGame(createdGame);
    }

    @Override
    public Game updateSchedule(Long id, LocalDateTime schedule) {
        log.info("Updating game's schedule with id: {} with {}", id, schedule);
        GameDto gameDto = gameRepository.updateSchedule(id, schedule);
        return convertDtoToGame(gameDto);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting game with id: {}", id);
        return gameRepository.delete(id);
    }

    private Game convertDtoToGame(GameDto gameDto) {
        if(gameDto == null) return null;

        League league = leagueRepository.findById(gameDto.leagueId()).orElseThrow();
        Team teamA = teamRepository.findById(gameDto.teamAId()).orElseThrow();
        Team teamB = teamRepository.findById(gameDto.teamBId()).orElseThrow();

        return new Game(
            gameDto.id(),
            league,
            teamA,
            teamB,
            gameDto.schedule()
        );
    }
}
