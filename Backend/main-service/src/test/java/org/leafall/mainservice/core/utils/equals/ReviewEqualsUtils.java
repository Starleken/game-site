package org.leafall.mainservice.core.utils.equals;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ReviewEqualsUtils {

    public static void equal(ReviewResponseDto responseDto, ReviewCreateDto createDto) {
        assertEquals(responseDto.getGrade(), createDto.getGrade());
        assertEquals(responseDto.getContent(), createDto.getContent());
    }

    public static void equal(ReviewResponseDto responseDto, ReviewUpdateDto updateDto) {
        assertEquals(responseDto.getId(), updateDto.getId());
        assertEquals(responseDto.getContent(), updateDto.getContent());
        assertEquals(responseDto.getGrade(), updateDto.getGrade());
    }
}
