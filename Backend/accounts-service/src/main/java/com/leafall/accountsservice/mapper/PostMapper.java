package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.post.PostCreateDto;
import com.leafall.accountsservice.dto.post.PostResponseDto;
import com.leafall.accountsservice.dto.post.PostResponseShortDto;
import com.leafall.accountsservice.dto.post.PostUpdateDto;
import com.leafall.accountsservice.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FileMapper.class, CommentMapper.class})
public interface PostMapper {

    PostEntity mapToEntity(PostCreateDto createDto);

    PostResponseDto mapToDto(PostEntity entity);

    PostResponseShortDto mapToShortDto(PostEntity entity);

    default void update(PostEntity entity, PostUpdateDto updateDto) {
        entity.setHeader(updateDto.getHeader());
        entity.setContent(updateDto.getContent());
    }
}
