package org.leafall.mainservice.controller;

import jakarta.validation.constraints.Min;
import org.leafall.mainservice.entity.PostEntity;
import org.leafall.mainservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leafall.mainservice.dto.post.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.leafall.mainservice.utils.LogUtils.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponseShortDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                              @RequestParam(defaultValue = "5") @Min(0) Integer size) {
        log.info(getRequest(PostEntity.class, "Find all"));
        var result = postService.findAll(page, size);
        log.info(getRequest(PostEntity.class, result.getSize()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable long id) {
        log.info(getRequest(PostEntity.class, "find by id", "id", id));
        var entity = postService.findById(id);
        log.info(getRequest(PostEntity.class, "find by id", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostResponseDto> create(@ModelAttribute @Valid PostCreateDto createDto) {
        log.info(getRequest(PostEntity.class, "create", createDto));
        var entity = postService.create(createDto);
        log.info(getResultRequest(PostEntity.class, "created", entity));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> update(@RequestBody @Valid PostUpdateDto updateDto) {
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
    public ResponseEntity<PostResponseDto> changeImage(@ModelAttribute @Valid PostChangeImageDto imageDto) {
        log.info(getRequest(PostEntity.class, "change image", imageDto));
        var entity = postService.changeImage(imageDto);
        log.info(getResultRequest(PostEntity.class, "image changed", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
