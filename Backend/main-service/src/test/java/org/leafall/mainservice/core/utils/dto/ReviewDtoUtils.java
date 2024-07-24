package org.leafall.mainservice.core.utils.dto;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

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
