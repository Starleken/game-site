package org.leafall.mainservice.service.impl;

import org.leafall.mainservice.dto.comment.CommentCreateDto;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.leafall.mainservice.dto.comment.CommentUpdateDto;
import org.leafall.mainservice.entity.CommentEntity;
import org.leafall.mainservice.entity.PostEntity;
import org.leafall.mainservice.mapper.CommentMapper;
import org.leafall.mainservice.repository.CommentRepository;
import org.leafall.mainservice.repository.PostRepository;
import org.leafall.mainservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.leafall.mainservice.utils.ExceptionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto create(CommentCreateDto createDto) {
        var foundPost = postRepository.findById(createDto.getToPostId())
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(PostEntity.class));

        var toSave = commentMapper.mapToEntity(createDto);
        toSave.setPost(foundPost);

        var saved = commentRepository.save(toSave);
        return commentMapper.mapToDto(saved);
    }

    @Override
    public CommentResponseDto update(CommentUpdateDto updateDto) {
        var foundComment = commentRepository.findById(updateDto.getId())
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(CommentEntity.class));

        commentMapper.update(foundComment, updateDto);
        var updated = commentRepository.save(foundComment);

        return commentMapper.mapToDto(updated);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(CommentEntity.class));

        commentRepository.deleteById(id);
    }
}
