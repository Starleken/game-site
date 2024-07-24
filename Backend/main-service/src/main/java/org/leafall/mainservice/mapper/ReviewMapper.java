package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;
import org.leafall.mainservice.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewResponseDto mapToDto(ReviewEntity entity);

    ReviewEntity mapToEntity(ReviewCreateDto createDto);

    default void update(ReviewEntity entity, ReviewUpdateDto updateDto) {
        entity.setContent(updateDto.getContent());
        entity.setGrade(updateDto.getGrade());
    }
}
