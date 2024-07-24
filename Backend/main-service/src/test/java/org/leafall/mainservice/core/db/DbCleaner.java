package org.leafall.mainservice.core.db;


import lombok.RequiredArgsConstructor;
import org.leafall.mainservice.repository.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbCleaner {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;

    public void clear() {
        reviewRepository.deleteAll();
        commentRepository.deleteAll();
        postRepository.deleteAll();
        gameRepository.deleteAll();
        genreRepository.deleteAll();
    }
}
