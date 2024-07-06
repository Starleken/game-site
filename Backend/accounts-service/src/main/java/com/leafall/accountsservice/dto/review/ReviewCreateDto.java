package com.leafall.accountsservice.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewCreateDto {

    private String content;
    private int grade;
    private long gameId;
}
