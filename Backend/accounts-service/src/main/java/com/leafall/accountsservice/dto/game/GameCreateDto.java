package com.leafall.accountsservice.dto.game;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class GameCreateDto {

    private String name;
    private List<Long> genres;
    private String description;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MultipartFile headerImage;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<MultipartFile> images;
}
