package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.game.*;

import java.util.List;

public interface GameService {

    List<GameResponseShortDto> findAll();

    GameResponseDto findById(Long id);

    GameResponseDto create(GameCreateDto createDto);

    GameResponseDto update(GameUpdateDto updateDto);

    GameResponseDto changeImage(GameChangeImageDto changeImageDto);

    void deleteById(Long id);
}
