package com.leafall.accountsservice.dto.game;

import dto.FileResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponseShortDto {

    private Long id;
    private String name;
    private FileResponseDto headerImage;
}
