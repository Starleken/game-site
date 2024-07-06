package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.game.GameCreateDto;
import com.leafall.accountsservice.dto.game.GameResponseDto;
import com.leafall.accountsservice.dto.game.GameUpdateDto;
import com.leafall.accountsservice.entity.GameEntity;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GameEqualsUtils {

    public static void equal(GameEntity entity, GameResponseDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getRating(), dto.getRating());
        assertEquals(entity.getHeaderImage(), dto.getHeaderImage().getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
    }

    public static void equal(GameCreateDto createDto, GameResponseDto dto) {
        assertEquals(createDto.getName(), dto.getName());
        assertEquals(createDto.getDescription(), dto.getDescription());
        assertEquals(createDto.getGenres().size(), dto.getGenres().size());
    }

    public static void equal(GameUpdateDto updateDto, GameResponseDto dto) {
        assertEquals(updateDto.getId(), dto.getId());
        assertEquals(updateDto.getName(), dto.getName());
        assertEquals(updateDto.getGenres().size(), dto.getGenres().size());
        assertEquals(updateDto.getDescription(), dto.getDescription());
    }
}
