package com.leafall.accountsservice.dto.post;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostUpdateDto {

    private Long id;
    private String header;
    private String content;
}
