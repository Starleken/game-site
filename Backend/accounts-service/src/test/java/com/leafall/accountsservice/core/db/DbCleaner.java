package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbCleaner {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;

    public void clear() {
        reviewRepository.deleteAll();
        commentRepository.deleteAll();
        fileRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        gameRepository.deleteAll();
        genreRepository.deleteAll();
    }
}
