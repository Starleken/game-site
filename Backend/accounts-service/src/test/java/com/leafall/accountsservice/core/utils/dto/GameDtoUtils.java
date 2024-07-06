package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.dto.game.GameCreateDto;
import com.leafall.accountsservice.dto.game.GameUpdateDto;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

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
