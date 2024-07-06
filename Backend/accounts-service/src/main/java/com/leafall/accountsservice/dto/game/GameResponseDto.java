package com.leafall.accountsservice.dto.game;

import com.leafall.accountsservice.dto.file.FileResponseDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.entity.ReviewEntity;
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
