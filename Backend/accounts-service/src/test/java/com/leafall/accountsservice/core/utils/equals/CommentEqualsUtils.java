package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;
import com.leafall.accountsservice.entity.CommentEntity;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CommentEqualsUtils {

    public static void equal(CommentResponseDto entity, CommentCreateDto createDto) {
        assertEquals(createDto.getContent(), entity.getContent());
    }

    public static void equal(CommentResponseDto entity, CommentUpdateDto updateDto) {
        assertEquals(updateDto.getId(), entity.getId());
        assertEquals(updateDto.getContent(), entity.getContent());
    }
}
