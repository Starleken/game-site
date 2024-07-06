package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.dto.review.ReviewCreateDto;
import com.leafall.accountsservice.dto.review.ReviewUpdateDto;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class ReviewDtoUtils {

    public static ReviewCreateDto generateCreateDto(long gameId) {
        return ReviewCreateDto.builder()
                .content(faker.lorem().sentence())
                .gameId(gameId)
                .grade(faker.number().numberBetween(1,5))
                .build();
    }

    public static ReviewUpdateDto generateUpdateDto(long id) {
        return ReviewUpdateDto.builder()
                .id(id)
                .content(faker.lorem().sentence())
                .grade(faker.number().numberBetween(1,5))
                .build();
    }
}
