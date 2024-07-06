package com.leafall.accountsservice.dto.game;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GameUpdateDto {

    private Long id;
    private String name;
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Long> genres;
}
