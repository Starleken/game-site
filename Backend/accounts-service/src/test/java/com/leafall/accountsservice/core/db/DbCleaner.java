package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.repository.CommentRepository;
import com.leafall.accountsservice.repository.FileRepository;
import com.leafall.accountsservice.repository.PostRepository;
import com.leafall.accountsservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbCleaner {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void clear() {
        commentRepository.deleteAll();
        fileRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
}
