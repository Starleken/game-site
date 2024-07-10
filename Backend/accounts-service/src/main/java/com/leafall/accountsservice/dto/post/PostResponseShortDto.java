package com.leafall.accountsservice.dto.post;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PostResponseShortDto {

    private Long id;
    private String header;
    private Long createdAt;
}
