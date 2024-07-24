package org.leafall.mainservice.core.db;

import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.entity.ReviewEntity;
import org.leafall.mainservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.leafall.mainservice.core.utils.entity.ReviewEntityUtils.*;

@Component
@RequiredArgsConstructor
public class ReviewDbHelper {

    private final ReviewRepository reviewRepository;

    public ReviewEntity saveReview(GameEntity game) {
        var generated = generateReview(game);
        return reviewRepository.save(generated);
    }
}
