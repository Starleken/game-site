package org.leafall.mainservice.core.db;

import org.leafall.mainservice.entity.PostEntity;
import org.leafall.mainservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.leafall.mainservice.core.utils.entity.PostEntityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.leafall.mainservice.core.utils.FileUtils.*;

@Component
@RequiredArgsConstructor
public class PostDbHelper {

    private final PostRepository repository;

    private final FileDbHelper fileDbHelper;
    private final CommentDbHelper commentDbHelper;

    public PostEntity save() {
        var generated = PostEntityUtils.generate(getFileResponseDto());

        return repository.save(generated);
    }

    public PostEntity saveWithComments(int commentsCount) {
        var generated = PostEntityUtils.generate(getFileResponseDto());

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
            var generated = PostEntityUtils.generate(getFileResponseDto());
            entities.add(generated);
        }


        return repository.saveAll(entities);
    }

    public Optional<PostEntity> findById(Long id) {
        return repository.findById(id);
    }
}
