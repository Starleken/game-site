package com.leafall.accountsservice.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long createdAt;
    private String createdBy;
}
