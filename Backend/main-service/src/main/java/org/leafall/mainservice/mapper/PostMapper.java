package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.post.PostCreateDto;
import org.leafall.mainservice.dto.post.PostResponseDto;
import org.leafall.mainservice.dto.post.PostResponseShortDto;
import org.leafall.mainservice.dto.post.PostUpdateDto;
import org.leafall.mainservice.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface PostMapper {

    PostEntity mapToEntity(PostCreateDto createDto);

    PostResponseDto mapToDto(PostEntity entity);

    PostResponseShortDto mapToShortDto(PostEntity entity);

    default void update(PostEntity entity, PostUpdateDto updateDto) {
        entity.setHeader(updateDto.getHeader());
        entity.setContent(updateDto.getContent());
    }
}
