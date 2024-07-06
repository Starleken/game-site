package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.review.ReviewCreateDto;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import com.leafall.accountsservice.dto.review.ReviewUpdateDto;
import org.junit.jupiter.api.Assertions;

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
