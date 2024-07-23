package com.leafall.accountsservice.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreUpdateDto {

    @NotNull(message = "Request must contain id of the genre")
    private Long id;

    @NotBlank(message = "Request must contain a name of the genre")
    private String name;
}
