package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.entity.GenreEntityUtils;
import com.leafall.accountsservice.core.utils.equals.GenreEqualsUtils;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.leafall.accountsservice.core.utils.entity.GenreEntityUtils.*;

@Component
@RequiredArgsConstructor
public class GenreDbHelper {

    private final GenreRepository genreRepository;

    public GenreEntity saveGenre() {
        var generated = generate();
        return genreRepository.save(generated);
    }

    public List<GenreEntity> saveGenre(int count) {
        List<GenreEntity> genres = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var generated = generate();
            genres.add(genreRepository.save(generated));
        }

        return genres;
    }

    public Optional<GenreEntity> findById(Long id) {
        return genreRepository.findById(id);
    }
}
