package org.leafall.mainservice.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentUpdateDto {

    @NotNull(message = "Request must contain id of the comment")
    private Long id;

    @NotBlank(message = "Request must contain a content of the comment")
    private String content;
}
