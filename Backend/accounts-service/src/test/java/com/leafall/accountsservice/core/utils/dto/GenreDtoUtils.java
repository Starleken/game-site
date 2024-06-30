package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class GenreDtoUtils {

    public static GenreCreateDto generateCreateDto() {
        var createDto = new GenreCreateDto();
        createDto.setName(faker.lorem().sentence());

        return createDto;
    }

    public static GenreUpdateDto generateUpdateDto(long id) {
        return GenreUpdateDto.builder()
                .id(id)
                .name(faker.lorem().sentence())
                .build();
    }
}
