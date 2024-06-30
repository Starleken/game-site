package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;
import com.leafall.accountsservice.entity.GenreEntity;
import org.junit.jupiter.api.Assertions;

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
