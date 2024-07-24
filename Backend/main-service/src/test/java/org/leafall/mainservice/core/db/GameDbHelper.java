package org.leafall.mainservice.core.db;

import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.entity.GenreEntity;
import org.leafall.mainservice.repository.GameRepository;
import org.leafall.mainservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.leafall.mainservice.core.utils.FileUtils.getFileResponseDto;
import static org.leafall.mainservice.core.utils.entity.GameEntityUtils.*;
import static org.leafall.mainservice.core.utils.entity.ReviewEntityUtils.*;
import static org.leafall.mainservice.utils.ExceptionUtils.*;

@Component
@RequiredArgsConstructor
public class GameDbHelper {

    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final GenreDbHelper genreDbHelper;
    private final FileDbHelper fileDbHelper;

    public GameEntity saveGame() {
        var generated = generate(getFileResponseDto());

        return gameRepository.save(generated);
    }

    public List<GameEntity> saveGame(int count) {
        List<GameEntity> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var generated = generate(getFileResponseDto());
            entities.add(generated);
        }

        return gameRepository.saveAll(entities);
    }

    public GameEntity saveGameWithReviews(int reviewCount) {
        var savedGame = saveGame();

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
