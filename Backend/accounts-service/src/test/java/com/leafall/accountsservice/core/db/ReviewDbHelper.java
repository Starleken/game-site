package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.entity.ReviewEntity;
import com.leafall.accountsservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.leafall.accountsservice.core.utils.entity.ReviewEntityUtils.*;

@Component
@RequiredArgsConstructor
public class ReviewDbHelper {

    private final ReviewRepository reviewRepository;

    public ReviewEntity saveReview(GameEntity game) {
        var generated = generateReview(game);
        return reviewRepository.save(generated);
    }
}
