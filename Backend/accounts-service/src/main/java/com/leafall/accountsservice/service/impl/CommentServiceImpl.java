package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;
import com.leafall.accountsservice.entity.CommentEntity;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.mapper.CommentMapper;
import com.leafall.accountsservice.repository.CommentRepository;
import com.leafall.accountsservice.repository.PostRepository;
import com.leafall.accountsservice.service.CommentService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto create(CommentCreateDto createDto) {
        var foundPost = postRepository.findById(createDto.getToPostId())
                .orElseThrow(() -> getEntityNotFoundException(PostEntity.class));

        var toSave = commentMapper.mapToEntity(createDto);
        toSave.setPost(foundPost);

        var saved = commentRepository.save(toSave);
        return commentMapper.mapToDto(saved);
    }

    @Override
    public CommentResponseDto update(CommentUpdateDto updateDto) {
        var foundComment = commentRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(CommentEntity.class));

        commentMapper.update(foundComment, updateDto);
        var updated = commentRepository.save(foundComment);

        return commentMapper.mapToDto(updated);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(CommentEntity.class));

        commentRepository.deleteById(id);
    }
}
