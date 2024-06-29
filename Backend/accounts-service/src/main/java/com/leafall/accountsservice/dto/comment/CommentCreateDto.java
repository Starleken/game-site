package com.leafall.accountsservice.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreateDto {

    private Long toPostId;
    private String content;
}
