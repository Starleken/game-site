package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.comment.CommentCreateDto;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.leafall.mainservice.dto.comment.CommentUpdateDto;

public interface CommentService {

    CommentResponseDto create(CommentCreateDto createDto);
    CommentResponseDto update(CommentUpdateDto updateDto);
    void deleteById(long id);
}
