package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.game.GameCreateDto;
import org.leafall.mainservice.dto.game.GameResponseDto;
import org.leafall.mainservice.dto.game.GameResponseShortDto;
import org.leafall.mainservice.dto.game.GameUpdateDto;
import org.leafall.mainservice.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface GameMapper {

    @Mapping(target = "headerImage", ignore = true)
    GameResponseShortDto mapToShortDto(GameEntity entity);

    @Mapping(target = "headerImage", ignore = true)
    GameResponseDto mapToDto(GameEntity entity);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "headerImage", ignore = true)
    GameEntity mapToEntity(GameCreateDto createDto);

    default void update(GameEntity entity, GameUpdateDto updateDto) {
        entity.setDescription(updateDto.getDescription());
        entity.setName(updateDto.getName());
    }
}
