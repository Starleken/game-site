package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.entity.FileEntity;
import com.leafall.accountsservice.entity.GameEntity;

import static com.leafall.accountsservice.core.utils.FakerUtils.faker;

public abstract class GameEntityUtils {

    public static GameEntity generate(FileEntity image) {
        var entity = new GameEntity();
        entity.setHeaderImage(image.getId());
        entity.setName(faker.name().title());
        entity.setDescription(faker.lorem().paragraph());

        return entity;
    }
}
