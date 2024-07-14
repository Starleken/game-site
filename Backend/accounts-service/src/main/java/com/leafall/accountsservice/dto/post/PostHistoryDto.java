package com.leafall.accountsservice.dto.post;

import dto.FileResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostHistoryDto {

    private Long id;
    private String header;
    private String content;
    private String createdBy;
}
