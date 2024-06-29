package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.entity.CommentEntityUtils;
import com.leafall.accountsservice.entity.CommentEntity;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.leafall.accountsservice.core.utils.entity.CommentEntityUtils.*;

@Component
@RequiredArgsConstructor
public class CommentDbHelper {

    private final CommentRepository commentRepository;

    public CommentEntity save(PostEntity post) {
        var generated = generate(post);
        return commentRepository.save(generated);
    }

    public Optional<CommentEntity> findById(Long id) {
        return commentRepository.findById(id);
    }
}
