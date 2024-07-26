package org.leafall.mainservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.leafall.mainservice.exception.EntityNotFoundException;
import org.leafall.mainservice.mapper.CommentMapper;
import org.leafall.mainservice.repository.CommentRepository;
import org.leafall.mainservice.repository.PostRepository;
import org.leafall.mainservice.service.impl.CommentServiceImpl;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.leafall.mainservice.core.utils.dto.CommentDtoUtils.*;
import static org.leafall.mainservice.core.utils.entity.CommentEntityUtils.*;
import static org.leafall.mainservice.core.utils.entity.PostEntityUtils.*;
import static org.leafall.mainservice.core.utils.equals.CommentEqualsUtils.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testCreate_happyPath() {
        //given
        var postId = 1L;
        var generatedPost = generatePost();
        generatedPost.setId(postId);
        var createDto = generateCreateDto(generatedPost.getId());

        when(postRepository.findById(postId)).thenReturn(Optional.of(generatedPost));
        when(commentRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = commentService.create(createDto);

        //then
        equal(responseDto, createDto);
    }

    @Test
    void testCreate_whenPostNotFound() {
        //given
        var idToSearch = 1L;
        var createDto = generateCreateDto(idToSearch);

        when(postRepository.findById(createDto.getToPostId())).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> commentService.create(createDto));

        //then
    }

    @Test
    void testUpdate_happyPath() {
        //given
        var generatedPost = generatePost();
        var generatedComment = generateComment(generatedPost);
        generatedComment.setId(1L);
        var updateDto = generateUpdateDto(generatedComment.getId());

        when(commentRepository.findById(updateDto.getId())).thenReturn(Optional.of(generatedComment));
        when(commentRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = commentService.update(updateDto);

        //then
        equal(responseDto, updateDto);
    }

    @Test
    void testUpdate_whenCommentNotFound() {
        //given
        var idToSearch = 1L;
        var updateDto = generateUpdateDto(idToSearch);

        when(commentRepository.findById(updateDto.getId())).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> commentService.update(updateDto));

        //then
    }

    @Test
    void testDeleteById_happyPath() {
        //given
        var idToDelete = 1L;
        var generatedPost = generatePost();
        var generatedComment = generateComment(generatedPost);
        generatedComment.setId(idToDelete);

        when(commentRepository.findById(idToDelete)).thenReturn(Optional.of(generatedComment));

        //when
        commentService.deleteById(generatedComment.getId());

        //then
        verify(commentRepository).deleteById(generatedComment.getId());
    }

    @Test
    void testDeleteById_whenCommentNotFound() {
        //given
        var idToDelete = 1L;

        when(commentRepository.findById(idToDelete)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> commentService.deleteById(idToDelete));

        //then
    }
}
