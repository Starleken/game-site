package org.leafall.mainservice.core.db;

import org.leafall.mainservice.entity.CommentEntity;
import org.leafall.mainservice.entity.PostEntity;
import org.leafall.mainservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.leafall.mainservice.core.utils.entity.CommentEntityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentDbHelper {

    private final CommentRepository commentRepository;

    public CommentEntity save(PostEntity post) {
        var generated = CommentEntityUtils.generateComment(post);
        return commentRepository.save(generated);
    }

    public Optional<CommentEntity> findById(Long id) {
        return commentRepository.findById(id);
    }
}
