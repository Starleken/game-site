package com.leafall.accountsservice.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDto {

    @NotBlank(message = "Request must contain a header of the post")
    private String header;

    @NotBlank(message = "Request must contain a content of the post")
    private String content;

    @NotNull(message = "Request must contain a image of the post")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
