package com.leafall.accountsservice.dto.game;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class GameCreateDto {

    @NotBlank(message = "Request must contain a name of the game")
    private String name;
    private List<Long> genres;

    @NotBlank(message = "Request must contain a description of the game")
    private String description;

    @NotNull(message = "Request must contain an image of the game")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MultipartFile headerImage;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<MultipartFile> images;
}
