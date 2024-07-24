package org.leafall.mainservice.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long createdAt;
    private String createdBy;
}
