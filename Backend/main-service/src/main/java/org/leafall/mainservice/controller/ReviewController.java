package org.leafall.mainservice.controller;

import org.leafall.mainservice.dto.review.ReviewCreateDto;
import org.leafall.mainservice.dto.review.ReviewResponseDto;
import org.leafall.mainservice.dto.review.ReviewUpdateDto;
import org.leafall.mainservice.entity.ReviewEntity;
import org.leafall.mainservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.leafall.mainservice.utils.LogUtils.getRequest;
import static org.leafall.mainservice.utils.LogUtils.getResultRequest;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody @Valid ReviewCreateDto createDto) {
        log.info(getRequest(ReviewEntity.class, "create", createDto));
        var result = reviewService.create(createDto);
        log.info(getResultRequest(ReviewEntity.class, "created", result));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ReviewResponseDto> update(@RequestBody @Valid ReviewUpdateDto updateDto) {
        log.info(getRequest(ReviewEntity.class, "update", updateDto));
        var result = reviewService.update(updateDto);
        log.info(getResultRequest(ReviewEntity.class, "updated", result));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info(getRequest(ReviewEntity.class, "delete", "id", id));
        reviewService.deleteById(id);
        log.info(getResultRequest(ReviewEntity.class, "deleted", "id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
