package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.genre.GenreCreateDto;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.leafall.mainservice.dto.genre.GenreUpdateDto;
import org.leafall.mainservice.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponseDto mapToDto(GenreEntity entity);

    GenreEntity mapToEntity(GenreCreateDto createDto);

    default void update(GenreEntity entity, GenreUpdateDto updateDto) {
        entity.setName(updateDto.getName());
    }
}
