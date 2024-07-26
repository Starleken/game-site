package org.leafall.mainservice.core.db;

import org.leafall.mainservice.entity.GenreEntity;
import org.leafall.mainservice.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.leafall.mainservice.core.utils.entity.GenreEntityUtils.*;

@Component
@RequiredArgsConstructor
public class GenreDbHelper {

    private final GenreRepository genreRepository;

    public GenreEntity saveGenre() {
        var generated = generateGenre();
        return genreRepository.save(generated);
    }

    public List<GenreEntity> saveGenre(int count) {
        List<GenreEntity> genres = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var generated = generateGenre();
            genres.add(genreRepository.save(generated));
        }

        return genres;
    }

    public Optional<GenreEntity> findById(Long id) {
        return genreRepository.findById(id);
    }
}
