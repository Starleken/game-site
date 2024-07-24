package org.leafall.mainservice.core.utils.dto;

import org.leafall.mainservice.dto.game.GameCreateDto;
import org.leafall.mainservice.dto.game.GameUpdateDto;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

public abstract class GameDtoUtils {

    public static GameCreateDto generateCreateDto() {
        return GameCreateDto.builder()
                .name(faker.name().name())
                .description(faker.lorem().paragraph())
                .build();
    }

    public static GameUpdateDto generateUpdateDto(Long id) {
        return GameUpdateDto.builder()
                .id(id)
                .name(faker.name().name())
                .description(faker.lorem().paragraph())
                .build();
    }
}
