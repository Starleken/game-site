package org.leafall.mainservice.dto.game;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@Data
@Builder
public class GameResponseDto {

    private Long id;
    private String name;
    private FileResponseDto headerImage;
    private String description;
    private Float rating;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<UUID> images;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GenreResponseDto> genres;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReviewResponseDto> reviews;
}
