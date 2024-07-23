package com.leafall.accountsservice.dto.game;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GameUpdateDto {

    @NotNull(message = "Request must contain id of the game")
    private Long id;

    @NotBlank(message = "Request must contain a name of the game")
    private String name;

    @NotBlank(message = "Request must contain a description of the game")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Long> genres;
}
