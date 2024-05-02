package com.dorayd.sports.features.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.With;

import java.time.LocalDateTime;

public record GameDto(@With Long id, Long leagueId, Long teamAId, Long teamBId, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime schedule) { }