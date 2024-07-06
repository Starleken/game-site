package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.review.ReviewCreateDto;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import com.leafall.accountsservice.dto.review.ReviewUpdateDto;
import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.entity.ReviewEntity;
import com.leafall.accountsservice.mapper.ReviewMapper;
import com.leafall.accountsservice.repository.GameRepository;
import com.leafall.accountsservice.repository.ReviewRepository;
import com.leafall.accountsservice.service.ReviewService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto create(ReviewCreateDto createDto) {
        var foundGame = gameRepository.findById(createDto.getGameId())
                .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));

        var entity = reviewMapper.mapToEntity(createDto);
        entity.setGame(foundGame);

        var saved = reviewRepository.save(entity);
        return reviewMapper.mapToDto(saved);
    }

    @Override
    public ReviewResponseDto update(ReviewUpdateDto updateDto) {
        var foundReview = reviewRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(ReviewEntity.class));

        reviewMapper.update(foundReview, updateDto);

        var updated = reviewRepository.save(foundReview);
        return reviewMapper.mapToDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        var found = reviewRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(ReviewEntity.class));

        reviewRepository.deleteById(found.getId());
    }
}
