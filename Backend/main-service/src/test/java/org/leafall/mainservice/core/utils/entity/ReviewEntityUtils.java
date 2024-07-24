package org.leafall.mainservice.core.utils.entity;

import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.entity.ReviewEntity;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

public abstract class ReviewEntityUtils {

    public static ReviewEntity generateReview(GameEntity game) {
        var generated = new ReviewEntity();
        generated.setGrade(faker.number().numberBetween(1,5));
        generated.setContent(faker.lorem().sentence());
        generated.setGame(game);

        return generated;
    }
}
