package org.leafall.mainservice.service.impl;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;
import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.entity.ReviewEntity;
import org.leafall.mainservice.mapper.ReviewMapper;
import org.leafall.mainservice.repository.GameRepository;
import org.leafall.mainservice.repository.ReviewRepository;
import org.leafall.mainservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.leafall.mainservice.utils.ExceptionUtils.*;

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
