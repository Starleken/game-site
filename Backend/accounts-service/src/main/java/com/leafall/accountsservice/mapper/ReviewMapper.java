package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.review.ReviewCreateDto;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import com.leafall.accountsservice.dto.review.ReviewUpdateDto;
import com.leafall.accountsservice.entity.ReviewEntity;
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
