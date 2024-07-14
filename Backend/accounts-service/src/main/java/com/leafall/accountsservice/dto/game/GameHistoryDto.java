package com.leafall.accountsservice.dto.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameHistoryDto {

    private Long id;
    private String name;
    private String description;
}
