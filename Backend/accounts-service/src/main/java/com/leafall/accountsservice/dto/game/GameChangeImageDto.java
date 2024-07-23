package com.leafall.accountsservice.dto.game;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class GameChangeImageDto {

    @NotNull(message = "Request must contain id of the game")
    private Long id;

    @NotNull(message = "Request must contain an image of the game")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
