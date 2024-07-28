package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.game.*;
import org.springframework.data.domain.Page;

public interface GameService {

    Page<GameResponseShortDto> findAll(int page, int size);

    GameResponseDto findById(Long id);

    GameResponseDto create(GameCreateDto createDto);

    GameResponseDto update(GameUpdateDto updateDto);

    GameResponseDto changeImage(GameChangeImageDto changeImageDto);

    void deleteById(Long id);
}
