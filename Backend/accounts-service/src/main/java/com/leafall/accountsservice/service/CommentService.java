package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;

public interface CommentService {

    CommentResponseDto create(CommentCreateDto createDto);
    CommentResponseDto update(CommentUpdateDto updateDto);
    void deleteById(long id);
}
