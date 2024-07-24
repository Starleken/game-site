package org.leafall.mainservice.dto.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewUpdateDto {

    @NotNull(message = "Request must contain id of the review")
    private Long id;

    @NotBlank(message = "Request must contain a content of the review")
    private String content;

    @Min(value = 0, message = "The grade of the post can't be lower than 0")
    private int grade;
}
