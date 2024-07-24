package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;

public interface ReviewService {

    ReviewResponseDto create(ReviewCreateDto createDto);

    ReviewResponseDto update(ReviewUpdateDto updateDto);

    void deleteById(Long id);
}
