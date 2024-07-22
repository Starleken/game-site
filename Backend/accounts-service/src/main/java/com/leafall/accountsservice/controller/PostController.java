package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.post.*;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.service.PostService;
import com.leafall.accountsservice.utils.LogUtils;
import com.leafall.tokenservice.service.AuthContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leafall.accountsservice.utils.LogUtils.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseShortDto>> findAll() {
        log.info(getRequest(PostEntity.class, "Find all"));
        var entities = postService.findAll();
        log.info(getRequest(PostEntity.class, entities.size()));

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable long id) {
        log.info(getRequest(PostEntity.class, "find by id", "id", id));
        var entity = postService.findById(id);
        log.info(getRequest(PostEntity.class, "find by id", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostResponseDto> create(@ModelAttribute PostCreateDto createDto) {
        log.info(getRequest(PostEntity.class, "create", createDto));
        var entity = postService.create(createDto);
        log.info(getResultRequest(PostEntity.class, "created", entity));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> update(@RequestBody PostUpdateDto updateDto) {
        log.info(getRequest(PostEntity.class, "update", updateDto));
        var entity = postService.update(updateDto);
        log.info(getResultRequest(PostEntity.class, "updated", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info(getRequest(PostEntity.class, "delete", "id", id));
        postService.deleteById(id);
        log.info(getResultRequest(PostEntity.class, "deleted", "id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/image")
    public ResponseEntity<PostResponseDto> changeImage(@ModelAttribute PostChangeImageDto imageDto) {
        log.info(getRequest(PostEntity.class, "change image", imageDto));
        var entity = postService.changeImage(imageDto);
        log.info(getResultRequest(PostEntity.class, "image changed", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
