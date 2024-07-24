package org.leafall.mainservice.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {

    private Long id;
    private String content;
    private int grade;
    private Long createdAt;
    private String createdBy;
}
