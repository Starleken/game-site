package com.leafall.accountsservice.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentHistoryDto {

    private Long id;
    private String content;

    private String createdBy;
}
