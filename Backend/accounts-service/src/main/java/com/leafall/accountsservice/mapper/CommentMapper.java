package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;
import com.leafall.accountsservice.entity.CommentEntity;
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
