package com.leafall.accountsservice.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostUpdateDto {

    @NotNull(message = "Request must contain id of the post")
    private Long id;

    @NotBlank(message = "Request must contain a header of the post")
    private String header;

    @NotBlank(message = "Request must contain a content of the post")
    private String content;
}
