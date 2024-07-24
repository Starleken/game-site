package org.leafall.mainservice.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewHistoryDto {

    private Long id;
    private String content;
    private int grade;
    private String createdBy;
}
