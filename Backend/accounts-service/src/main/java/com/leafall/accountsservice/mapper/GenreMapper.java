package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;
import com.leafall.accountsservice.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponseDto mapToDto(GenreEntity entity);

    GenreEntity mapToEntity(GenreCreateDto createDto);

    default void update(GenreEntity entity, GenreUpdateDto updateDto) {
        entity.setName(updateDto.getName());
    }
}
