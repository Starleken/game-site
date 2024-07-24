package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.comment.CommentCreateDto;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.leafall.mainservice.dto.comment.CommentUpdateDto;
import org.leafall.mainservice.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    CommentEntity mapToEntity(CommentCreateDto createDto);

    CommentResponseDto mapToDto(CommentEntity entity);

    default void update(CommentEntity entity, CommentUpdateDto updateDto) {
        entity.setContent(updateDto.getContent());
    }
}
