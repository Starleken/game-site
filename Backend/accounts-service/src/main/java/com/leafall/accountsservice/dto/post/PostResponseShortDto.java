package com.leafall.accountsservice.dto.post;

import com.leafall.accountsservice.dto.file.FileResponseDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PostResponseShortDto {

    private Long id;
    private String header;
    private Long createdAt;
}
