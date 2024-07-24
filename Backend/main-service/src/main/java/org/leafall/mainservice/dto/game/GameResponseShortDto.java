package org.leafall.mainservice.dto.game;

import lombok.Builder;
import lombok.Data;
import org.leafall.fileservicestarter.dto.FileResponseDto;

@Data
@Builder
public class GameResponseShortDto {

    private Long id;
    private String name;
    private FileResponseDto headerImage;
}
