package org.leafall.mainservice.core.utils.dto;

import org.leafall.mainservice.dto.genre.GenreCreateDto;
import org.leafall.mainservice.dto.genre.GenreUpdateDto;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

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
