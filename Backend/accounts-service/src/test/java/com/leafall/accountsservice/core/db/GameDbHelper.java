package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.entity.ReviewEntityUtils;
import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.entity.ReviewEntity;
import com.leafall.accountsservice.repository.GameRepository;
import com.leafall.accountsservice.repository.ReviewRepository;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.leafall.accountsservice.core.utils.entity.GameEntityUtils.*;
import static com.leafall.accountsservice.core.utils.entity.ReviewEntityUtils.*;
import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Component
@RequiredArgsConstructor
public class GameDbHelper {

    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final GenreDbHelper genreDbHelper;
    private final FileDbHelper fileDbHelper;

    public GameEntity saveGame(Class testClass) {
        var generated = generate(fileDbHelper.addFile(testClass));

        return gameRepository.save(generated);
    }

    public List<GameEntity> saveGame(int count, Class testClass) {
        List<GameEntity> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var generated = generate(fileDbHelper.addFile(testClass));
            entities.add(generated);
        }

        return gameRepository.saveAll(entities);
    }

    public GameEntity saveGameWithReviews(int reviewCount, Class testClass) {
        var savedGame = saveGame(testClass);

        for (int i = 0; i < reviewCount; i++) {
            reviewRepository.save(generateReview(savedGame));
        }

        return findById(savedGame.getId())
                .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));
    }

    public List<Long> saveGenres(int count) {
        return genreDbHelper.saveGenre(5).stream()
                .map(GenreEntity::getId)
                .toList();
    }

    public Optional<GameEntity> findById(long id) {
        return gameRepository.findById(id);
    }
}