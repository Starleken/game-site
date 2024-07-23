package com.leafall.accountsservice.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreateDto {

    @NotNull(message = "Request must contain id of the post")
    private Long toPostId;

    @NotBlank(message = "Request must contain a content of the comment")
    private String content;
}
