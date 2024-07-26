package org.leafall.mainservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.mainservice.core.utils.dto.ReviewDtoUtils;
import org.leafall.mainservice.core.utils.entity.GameEntityUtils;
import org.leafall.mainservice.core.utils.entity.ReviewEntityUtils;
import org.leafall.mainservice.core.utils.equals.ReviewEqualsUtils;
import org.leafall.mainservice.exception.EntityNotFoundException;
import org.leafall.mainservice.mapper.ReviewMapper;
import org.leafall.mainservice.repository.GameRepository;
import org.leafall.mainservice.repository.ReviewRepository;
import org.leafall.mainservice.service.impl.ReviewServiceImpl;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.leafall.mainservice.core.utils.dto.ReviewDtoUtils.*;
import static org.leafall.mainservice.core.utils.entity.GameEntityUtils.*;
import static org.leafall.mainservice.core.utils.entity.ReviewEntityUtils.*;
import static org.leafall.mainservice.core.utils.equals.ReviewEqualsUtils.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private GameRepository gameRepository;

    @Spy
    private ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void testCreate_happyPath() {
        //given
        var gameId = 1L;
        var generatedGame = generateGame();
        generatedGame.setId(gameId);
        var createDto = generateCreateDto(generatedGame.getId());

        when(gameRepository.findById(generatedGame.getId())).thenReturn(Optional.of(generatedGame));
        when(reviewRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = reviewService.create(createDto);

        //then
        equal(responseDto, createDto);
    }

    @Test
    void testCreate_whenGameNotFound() {
        //given
        var gameIdToSearch = 1L;
        var createDto = generateCreateDto(gameIdToSearch);

        when(gameRepository.findById(gameIdToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> reviewService.create(createDto));

        //then
    }

    @Test
    void testUpdate_happyPath() {
        //given
        var postId = 1L;
        var generatedGame = generateGame();
        var generatedReview = generateReview(generatedGame);
        generatedReview.setId(postId);
        var updateDto = generateUpdateDto(generatedReview.getId());

        when(reviewRepository.findById(postId)).thenReturn(Optional.of(generatedReview));
        when(reviewRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = reviewService.update(updateDto);

        //then
        equal(responseDto, updateDto);
    }

    @Test
    void testUpdate_whenReviewNotFound() {
        //given
        var idToSearch = 1L;
        var updateDto = generateUpdateDto(idToSearch);

        when(reviewRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> reviewService.update(updateDto));
    }

    @Test
    void testDeleteById_happyPath() {
        //given
        var idToDelete = 1L;
        var generatedGame = generateGame();
        var generatedReview = generateReview(generatedGame);
        generatedReview.setId(idToDelete);

        when(reviewRepository.findById(idToDelete)).thenReturn(Optional.of(generatedReview));

        //when
        reviewService.deleteById(idToDelete);

        //then
        verify(reviewRepository).deleteById(idToDelete);
    }

    @Test
    void testDeleteById_whenReviewNotFound() {
        //given
        var idToSearch = 1L;

        when(reviewRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> reviewService.deleteById(idToSearch));

        //then
    }
}
