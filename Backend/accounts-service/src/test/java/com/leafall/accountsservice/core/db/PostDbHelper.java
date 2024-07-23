package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.FileUtils;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.repository.CommentRepository;
import com.leafall.accountsservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.leafall.accountsservice.core.utils.FileUtils.*;
import static com.leafall.accountsservice.core.utils.entity.PostEntityUtils.*;

@Component
@RequiredArgsConstructor
public class PostDbHelper {

    private final PostRepository repository;

    private final FileDbHelper fileDbHelper;
    private final CommentDbHelper commentDbHelper;

    public PostEntity save() {
        var generated = generate(getFileResponseDto());

        return repository.save(generated);
    }

    public PostEntity saveWithComments(int commentsCount) {
        var generated = generate(getFileResponseDto());

        var savedPost = repository.save(generated);

        for (int i = 0; i < commentsCount; i++) {
            var comment = commentDbHelper.save(savedPost);
            savedPost.getComments().add(comment);
        }

        return savedPost;
    }

    public List<PostEntity> save(int count, Class testClass) {
        List<PostEntity> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var generated = generate(getFileResponseDto());
            entities.add(generated);
        }


        return repository.saveAll(entities);
    }

    public Optional<PostEntity> findById(Long id) {
        return repository.findById(id);
    }
}
