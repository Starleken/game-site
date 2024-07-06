package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.review.ReviewCreateDto;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import com.leafall.accountsservice.dto.review.ReviewUpdateDto;

public interface ReviewService {

    ReviewResponseDto create(ReviewCreateDto createDto);

    ReviewResponseDto update(ReviewUpdateDto updateDto);

    void deleteById(Long id);
}
