package org.leafall.mainservice.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreCreateDto {

    @NotBlank(message = "Request must contain a name of the genre")
    private String name;
}
