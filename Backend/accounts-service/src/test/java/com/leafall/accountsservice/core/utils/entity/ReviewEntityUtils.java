package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.entity.ReviewEntity;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class ReviewEntityUtils {

    public static ReviewEntity generateReview(GameEntity game) {
        var generated = new ReviewEntity();
        generated.setGrade(faker.number().numberBetween(1,5));
        generated.setContent(faker.lorem().sentence());
        generated.setGame(game);

        return generated;
    }
}
