package org.leafall.mainservice.core.utils.equals;

import org.leafall.mainservice.dto.genre.GenreCreateDto;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.leafall.mainservice.dto.genre.GenreUpdateDto;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GenreEqualsUtils {

    public static void equal(GenreResponseDto responseDto, GenreCreateDto createDto) {
        assertEquals(createDto.getName(), responseDto.getName());
    }

    public static void equal(GenreResponseDto responseDto, GenreUpdateDto updateDto) {
        assertEquals(updateDto.getId(), responseDto.getId());
        assertEquals(updateDto.getName(), responseDto.getName());
    }
}
