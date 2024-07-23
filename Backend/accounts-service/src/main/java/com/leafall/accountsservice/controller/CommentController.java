package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;
import com.leafall.accountsservice.entity.CommentEntity;
import com.leafall.accountsservice.service.CommentService;
import com.leafall.accountsservice.utils.LogUtils;
import jakarta.validation.Valid;
import liquibase.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.leafall.accountsservice.utils.LogUtils.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(@RequestBody @Valid CommentCreateDto createDto) {
        log.info(getRequest(CommentEntity.class, "create", createDto));
        var entity = commentService.create(createDto);
        log.info(getResultRequest(CommentEntity.class, "created", entity));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommentResponseDto> update(@RequestBody @Valid CommentUpdateDto updateDto) {
        log.info(getRequest(CommentEntity.class, "update", updateDto));
        var entity = commentService.update(updateDto);
        log.info(getResultRequest(CommentEntity.class, "updated", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info(getRequest(CommentEntity.class, "delete by id", "Id", id));
        commentService.deleteById(id);
        log.info(getResultRequest(CommentEntity.class, "deleted by id", "Id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
