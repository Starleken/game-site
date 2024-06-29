package com.leafall.accountsservice.dto.post;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostChangeImageDto {

    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
