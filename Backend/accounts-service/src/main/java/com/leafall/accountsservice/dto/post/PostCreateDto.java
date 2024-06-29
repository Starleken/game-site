package com.leafall.accountsservice.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDto {

    private String header;
    private String content;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
