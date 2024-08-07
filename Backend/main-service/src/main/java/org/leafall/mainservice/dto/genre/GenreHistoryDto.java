package org.leafall.mainservice.dto.genre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreHistoryDto {

    private Long id;
    private String name;
}
