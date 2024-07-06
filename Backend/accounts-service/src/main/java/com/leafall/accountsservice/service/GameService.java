package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.game.*;

import java.util.List;

public interface GameService {

    List<GameResponseShortDto> findAll();

    GameResponseDto findById(Long id);

    GameResponseDto create(GameCreateDto createDto);

    GameResponseDto update(GameUpdateDto updateDto);

    GameResponseDto changeImage(GameChangeImageDto changeImageDto);

    void deleteById(Long id);
}
