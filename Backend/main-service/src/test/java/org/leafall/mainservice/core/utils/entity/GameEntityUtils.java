package org.leafall.mainservice.core.utils.entity;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.mainservice.entity.GameEntity;

import static org.leafall.mainservice.core.utils.FakerUtils.faker;

public abstract class GameEntityUtils {

    public static GameEntity generateGame() {
        var entity = new GameEntity();
        entity.setName(faker.name().title());
        entity.setDescription(faker.lorem().paragraph());

        return entity;
    }

    public static GameEntity generateGame(FileResponseDto image) {
        var entity = generateGame();
        entity.setHeaderImage(image.getId());

        return entity;
    }
}
