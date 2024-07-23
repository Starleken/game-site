package com.leafall.accountsservice.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class GenreCreateDto {

    @NotBlank(message = "Request must contain a name of the genre")
    private String name;
}
