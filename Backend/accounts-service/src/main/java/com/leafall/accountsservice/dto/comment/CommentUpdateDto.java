package com.leafall.accountsservice.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentUpdateDto {

    private Long id;
    private String content;
}
